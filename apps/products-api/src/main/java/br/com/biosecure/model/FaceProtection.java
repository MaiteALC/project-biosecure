package br.com.biosecure.model;

import br.com.biosecure.utils.StringUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FaceProtection extends PPE {
    @Enumerated(EnumType.STRING)
    private ProtectionType protectionType;
    private String standardRating;
    private boolean hasValve;
    private boolean antiFog;

    protected FaceProtection(FaceProtectionBuilder builder) {
        super(builder);

        this.protectionType = builder.protectionType;
        this.standardRating = builder.standardRating;
        this.hasValve = builder.protectionType == ProtectionType.MASK_RESPIRATOR && builder.hasValve;
        this.antiFog = builder.antiFog;
    }

    public static FaceProtectionBuilder builder() {
        return new FaceProtectionBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class FaceProtectionBuilder extends PpeBuilder<FaceProtection, FaceProtectionBuilder> {
        private ProtectionType protectionType;
        private String standardRating;
        private boolean hasValve;
        private boolean antiFog;

        @Override
        protected FaceProtectionBuilder self() {
            return this;
        }

        @Override
        public FaceProtection build() {
            if (protectionType == null) {
                productNotification.addError("protection type", "protection type mustn't be null");
            }

            StringUtils.validateString(standardRating, 2, "standard rating", 12, true, super.productNotification);

            if (super.productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(super.productNotification.getErrors());
            }

            return new FaceProtection(this);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum ProtectionType {
        MASK_RESPIRATOR("MR"),
        SAFETY_GLASSES("SG"), 
        GOGGLES("GG"),       
        FACE_SHIELD("FS");

        private final String code;
    }
}
