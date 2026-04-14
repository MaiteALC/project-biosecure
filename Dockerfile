# ==========================================
# Stage 1: Build (Packaging the source code)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY .mvn/ ./.mvn
COPY mvnw pom.xml ./

RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw dependency:go-offline -DskipGitConfig

COPY src ./src

RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw clean package -DskipTests -DskipGitConfig spring-boot:repackage


# ==========================================
# Stage 2: Custom JRE (jdeps and jlink)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS jre-build

WORKDIR /build

COPY --from=build /app/target/customers-api*.jar app.jar

RUN unzip app.jar -d extracted

RUN MODULES=$(jdeps -q \
    --ignore-missing-deps \
    --recursive \
    --print-module-deps \
    --multi-release 21 \
    --class-path 'extracted/BOOT-INF/lib/*' \
    extracted/BOOT-INF/classes \
) && \
MODULES="$MODULES,jdk.crypto.ec,jdk.crypto.cryptoki,java.management,java.instrument" && \
jlink \
    --add-modules "$MODULES" \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /custom-jre


# ==========================================
# Stage 3: Final image build
# ==========================================
FROM alpine:3.19

WORKDIR /app

RUN apk add --no-cache tzdata

RUN addgroup -S biosecure && adduser -S biosecure -G biosecure
USER biosecure

COPY --from=jre-build /custom-jre /custom-jre
ENV PATH="/custom-jre/bin:$PATH"

COPY --from=build /app/target/customers-api*.jar customers-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "customers-api.jar"]