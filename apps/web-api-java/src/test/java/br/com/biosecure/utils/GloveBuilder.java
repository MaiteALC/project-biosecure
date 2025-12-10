package br.com.biosecure.utils;

import br.com.biosecure.model.product.Glove;
import br.com.biosecure.model.product.Glove.GloveMaterial;

public class GloveBuilder extends PersonalProtectiveEquipmentBuilder {
    // Specific attributes of Glove
    private boolean powderFree = true;
    private boolean longBarrel = true;
    private GloveMaterial material = GloveMaterial.NITRILE;
    private boolean isTextured = false;
    private double thicknessMils = 1.5;

    public GloveBuilder withPowderFree(boolean powderFree) {
        this.powderFree = powderFree;

        return this;
    }

    public GloveBuilder withLongBarrel(boolean longBarrel) {
        this.longBarrel = longBarrel;

        return this;
    }

    public GloveBuilder withMaterial(GloveMaterial material) {
        this.material = material;

        return this;
    }

    public GloveBuilder withTextured(boolean isTextured) {
        this.isTextured = isTextured;

        return this;
    }

    public GloveBuilder withThicknessMils(double thicknessMils) {
        this.thicknessMils = thicknessMils;

        return this;
    }

    @Override
    protected GloveBuilder self() {
        return this;
    }

    public static GloveBuilder aGlove() {
        return new GloveBuilder();
    }

    @Override
    public Glove build() {
        return new Glove(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, size, certificateOfApproval, isDisposable, powderFree, longBarrel, material, isTextured, thicknessMils);
    }
}
