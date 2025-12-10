package br.com.biosecure.utils;

import br.com.biosecure.model.product.FaceProtection;
import br.com.biosecure.model.product.FaceProtection.ProtectionType;

public class FaceProtectionBuilder extends PersonalProtectiveEquipmentBuilder {
    // Specific attributes of Face Protection
    private ProtectionType type = ProtectionType.MASK_RESPIRATOR;
    private String standardRating = "N95";
    private boolean isAntiFog = false;

    public FaceProtectionBuilder withType(ProtectionType type) {
        this.type = type;

        return this;
    }
    
    public FaceProtectionBuilder withStandardRating(String standardRating) {
        this.standardRating = standardRating;

        return this;
    }
    
    public FaceProtectionBuilder withAntiFog(boolean isAntiFog) {
        this.isAntiFog = isAntiFog;

        return this;
    }
    
    public static FaceProtectionBuilder aFaceProtection() {
        return new FaceProtectionBuilder();
    }

    @Override
    protected FaceProtectionBuilder self() {
        return this;
    }

    @Override
    public FaceProtection build() {
        return new FaceProtection(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, size, certificateOfApproval, isDisposable, type, standardRating, isAntiFog);
    }
}
