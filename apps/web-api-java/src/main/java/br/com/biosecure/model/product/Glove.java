package br.com.biosecure.model.product;

import java.time.LocalDate;

public class Glove extends PersonalProtectiveEquipment {
    private final boolean powderFree;
    private final boolean longBarrel;
    private final GloveMaterial material;
    private final boolean isTextured;
    private final double thicknessMils;

    public Glove(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable, boolean isPowderFree, boolean hasLongBarrel, GloveMaterial material, boolean isTextured, double thicknessMils) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, isDisposable);

        this.powderFree = isPowderFree;
        this.longBarrel = hasLongBarrel;
        this.material = material;
        this.isTextured = isTextured;
        this.thicknessMils = thicknessMils;
    }

    public enum GloveMaterial {
        LATEX,
        NITRILE, // The gold standard for labs
        VINYL,
        NEOPRENE;
    }

    public boolean isPowderFree() {
        return powderFree;
    }

    public boolean hasLongBarrel() {
        return longBarrel;
    }

    public GloveMaterial getMaterial() {
        return material;
    }

    public boolean isTextured() {
        return isTextured;
    }

    public double getThicknessMils() {
        return thicknessMils;
    }
}
