# 🧬 BioSecure Customers API

This repository contains the RESTful API responsible for handling all customer-related operations. It is a core microservice that makes up the BioSecure ecosystem.

> What is BioSecure? It's an enterprise software ecosystem mimicking real-world scenarios for a fictional B2B biosafety company.

Ecosystem Links:

[📦 Products API](https://github.com/MaiteALC/biosecure-products-api)

[🛠️ Commons lib](https://github.com/MaiteALC/biosecure-commons-lib)

**Current project status:** 🚧 In Development

---

## Tech Stack & Patterns

**Main tools:**
- **Language:** Java 21
- **Framework:** SpringBoot
- **Database:** PostgreSQL
- **Build Manager:** Maven

**Architecture & Design:**

This project adheres to Clean Architecture and Domain-Driven Design (DDD) principles. By creating concentric layers, the domain layer is protected and isolated from the external world. Furthermore, the strict use of Ubiquitous Language ensures domain accuracy and alignment with business rules. 

---

## 🏛️ Bounded Context

The main class in this project is the `Customer`, an aggregate root composed of:

- 🏷️ `Cnpj`: A value object representing a Brazilian Corporate Taxpayer Registry number.
- 💰 `FinancialData`: The internal and private financial profile of a customer.
- 📍 `Address`: A value object that encapsulates the address information.
- ⚖️ `TaxData`: The public statutory and legal tax profile of a customer.

Together, these classes compose a complete and valid customer state within the BioSecure domain.

---

## 🚀 How to Run

### Local Setup Prerequisites:
* Java 21
* PostgreSQL 17

```Bash
# Clone the repository
git clone https://github.com/MaiteALC/biosecure-customers-api

# Navigate into the directory
cd biosecure-customers-api

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

### 🐳 Running with Docker:

**Configuring the build environment**:

To build the image, Maven downloads all the dependencies inside the container.

This project depends on `commons-lib`, a BioSecure internal library available only on GitHub Packages.
Maven requires your GitHub credentials to log in and download this depency. Therefore, you need to provide these credentials via an XML file, which will be injected as a secure secret into the container during the build process (detailed in the next step).

Create a `settings-docker.xml` file, preferably in the project root, as the Docker Compose expects.

<details>
  <summary><b>View settings-docker.xml template</b></summary>
  <br>

> ⚠️ Important: The `<password>` must be a **Personal Access Token (PAT)** generated in your GitHub account with the `read:packages` scope enabled.

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>github</id> <!-- this id must be IDENTICAL
             to the repository id in the project's pom.xml file -->
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
        </server>
    </servers>
</settings>
```
</details>

\
**Running the container:**

**Option 1: The easy way (Docker Compose) 🌟**

The easiest way to spin up the entire environment (API + PostgreSQL database) is using Docker Compose. Make sure your settings.xml path matches the one defined in the docker-compose.yml secrets section, or provide your custom path (see the Environment Variables section for more details).

```Bash
docker compose up
```

**Option 2: Standalone API Container (Manual Run)**

If you prefer to run only the API container and use an external database, you must build the image manually and inject the database credentials via environment variables.

> Note: If you are using a local PostgreSQL instance, remember that localhost inside the container refers to the container itself. You must pass the correct IP address of your host machine, or use specific Docker network flags (like `--network host` on Linux or `host.docker.internal` on macOS/Windows) via the `SPRING_DATASOURCE_URL`.

The pre-built image is not available on DockerHub yet. At this moment, you need to build it locally.

Follow the instructions below to build the image and start the container:

```Bash
# 1. Build the image and give it a tag. 
# Do not forget the '.' at the end, and provide the correct path to your XML file!
docker build -t biosecure-customers-api \
--secret id=maven_settings,src=/path/to/your_maven_settings.xml .

# 2. Run the container with the image you just build (Example overriding the database variables) 
# Feel free to give it whatever name you want and map the ports to enable external world communication
docker run --name customers-api-container \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<YOUR_HOST_IP>:5432/your_db_name \
  -e SPRING_DATASOURCE_USERNAME=your_username \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  -e SPRING_FLYWAY_USER=your_username \
  -e SPRING_FLYWAY_PASSWORD=your_password \
  biosecure-customers-api
```    

---

## ⚙️ Environment Variables

The application is highly configurable. You can define the required environment variables in a `.env` file at the root of the project. The `application.yml` (via `spring.config.import` property) and Docker Compose will automatically read the file. Alternatively, you can override any property directly via CLI.

Refer to the table below to see the main variables you might need to modify:

|        Variable         |                                     Description                                      |  
|:-----------------------:|:------------------------------------------------------------------------------------:|
|        `DB_USER`        |                       The database username (no default value)                       | 
|      `DB_PASSWORD`      |                       The database password (no default value)                       |  
|        `DB_NAME`        |                     The database name (default to customers-db)                      | 
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL (default to `jdbc:postgresql://localhost:5432/${DB_NAME}`) |
|  `MAVEN_SETTINGS_PATH`  |    Path to your Maven XML configuration file (default to `./settings-docker.xml`)    |


> **Note:** If you use Docker Compose, the `SPRING_DATASOURCE_URL` will automatically be overwritten to `jdbc:postgresql://customers_db:5432/${DB_NAME}` to use the Docker's internal network.

---

## 📖 API Documentation

    TODO: Swagger UI / OpenAPI specification integration is planned for future releases. 

---

## 🔄 Project Evolution & Poly-repo Migration
BioSecure started as a monolithic multi-module project to facilitate early development. As the project scaled, modules like `commons-lib` and `customer/products-api` were extracted into their own repositories.

**Current Architecture:** Microservices/Poly-repo.

**Previous Architecture:** Monorepo

If you want to explore the original monolithic directory structure and early evolution, you can browse the v1.0.0-monolithic-era snapshot [here](https://github.com/MaiteALC/biosecure-customers-api/releases/tag/v1.0.0-monolithic-era).