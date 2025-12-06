package br.com.biosecure.model.product;

import java.time.LocalDate;

public class FaceProtection extends PersonalProtectiveEquipment {
    private final ProtectionType type;
    private final String standardRating;
    private final boolean hasValve;
    private final boolean isAntiFog;

    public FaceProtection(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable, ProtectionType type, String standardRating, boolean isAntiFog) {

        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, isDisposable);

        validateString(standardRating, "standard rating");

        this.type = type;
        this.standardRating = standardRating;
        this.hasValve = type != ProtectionType.MASK_RESPIRATOR ? false : true;
        this.isAntiFog = type == ProtectionType.MASK_RESPIRATOR ? false : isAntiFog;
    }

    public enum ProtectionType {
        MASK_RESPIRATOR("MR"),
        SAFETY_GLASSES("SG"), 
        GOGGLES("GG"),       
        FACE_SHIELD("FS");

        private final String code;

        ProtectionType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public ProtectionType getProtectionType() {
        return type;
    }

    public String getStandardRating() {
        return standardRating;
    }

    public boolean hasValve() {
        return hasValve;
    }

    public boolean isAntiFog() {
        return isAntiFog;
    }
}
