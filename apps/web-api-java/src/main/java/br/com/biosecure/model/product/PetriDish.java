package br.com.biosecure.model.product;

import java.time.LocalDate;

public class PetriDish extends SampleContainer {
    private final int divNum;
    private final boolean grid;
    private final boolean ventilated;
    private final double diameterMm;
    private final double heightMm;

    public PetriDish(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, SterilizationMethod sterilizationMethod, ClosingMethod closingMethod, Material materialType, int divisionsNumber, boolean hasGrid, boolean isVentilated, double diameterMm, double heightMm) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, materialType, calculateNominalCapacity(diameterMm, heightMm));

        if (divisionsNumber < 1 || divisionsNumber > 4) {
            throw new InvalidProductAttributeException("divisions number");
        }

        this.divNum = divisionsNumber;
        this.grid = hasGrid;
        this.ventilated = isVentilated;
        this.diameterMm = diameterMm;
        this.heightMm = heightMm;
    }

    public int getDivisionsNumber() {
        return divNum;
    }

    public boolean isGrid() {
        return grid;
    }

    public boolean isVentilated() {
        return ventilated;
    }

    public double getDiameter() {
        return diameterMm;
    }

    public double getHeight() {
        return heightMm;
    }

    public static double calculateSurfaceAreaPerDiv(double diameter, int divNum) {
        double radius = diameter / 2.0;

        return (Math.PI * Math.pow(radius, 2)) / divNum;
    }

    public static double calculateNominalCapacity(double diameter, double height) {
        // V = pi * r^2 * h
        double radius = diameter / 2.0;

        double usefulHeight = height / 2.0;

        double volumeCubicMm = Math.PI * Math.pow(radius, 2) * usefulHeight;

        return Math.round(volumeCubicMm / 1000.0); // Conversion of mm^3 to mL
    }
}
