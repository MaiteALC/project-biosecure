package br.com.biosecure.utils;

import br.com.biosecure.model.product.LabCoat;
import br.com.biosecure.model.product.LabCoat.*;

public class LabCoatBuilder extends PersonalProtectiveEquipmentBuilder {
    // Specific attributes of Lab Coat
    private FabricType fabricType = FabricType.COTTON_100;
    private int grammage = 40;
    private CuffStyle cuffStyle = CuffStyle.KNITTED_CUFF;
    private CollarType collarType = CollarType.HIGH_NECK;

    public LabCoatBuilder withFabricType(FabricType fabricType) {
        this.fabricType = fabricType;

        return this;
    }

    public LabCoatBuilder withGrammage(int grammage) {
        this.grammage = grammage;

        return this;
    }

    public LabCoatBuilder withCuffStyle(CuffStyle cuffStyle) {
        this.cuffStyle = cuffStyle;

        return this;
    }

    public LabCoatBuilder withCollarType(CollarType collarType) {
        this.collarType = collarType;

        return this;
    }

    @Override
    protected LabCoatBuilder self() {
        return this;
    }

    public static LabCoatBuilder aLabCoat() {
        return new LabCoatBuilder();
    }

    @Override
    public LabCoat build() {
        return new LabCoat(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, size, certificateOfApproval, fabricType, grammage, cuffStyle, collarType);
    }
}
