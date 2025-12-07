package br.com.biosecure.model.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    /**
     * Calculates the nominal capacity (volume) of a Petri Dish in mililiters.
     * <p>
     * Important note: This method calculates considering <strong>useful height of 50%</strong> of given total height
     * considering that the petri dish is never fully filled.
     * The result is rounded to 2 decimal places.
     * </p>
     * 
     * <p>
     * The calculation uses the formula for the volume of a cylinder.: {@code V = π * r² * h}.
     * </p>
     *
     * @param diameter Total diameter in milimeters (mm).
     * Must be betweeen 1 and 9999.
     * @param height   Total height in milimeters (mm).
     * Must be between 1 and 9999.
     * @return The calculated capacity in mililiters (mL).
     * @throws InvalidProductAttributeException If dimension is out of allowed limits (<1 or >9999).
     */
    public static double calculateNominalCapacity(double diameter, double height) {
        if (diameter < 1 || height < 1 || diameter > 9999 || height > 9999) {
            throw new InvalidProductAttributeException("physical dimensions");
        }

        BigDecimal usefulHeight = BigDecimal.valueOf(height / 2.0).setScale(2, RoundingMode.DOWN);

        double volumeCubicMm = Math.PI * Math.pow(diameter / 2.0, 2) * usefulHeight.doubleValue();

        BigDecimal volumeML =  BigDecimal.valueOf(volumeCubicMm / 1000).setScale(2, RoundingMode.HALF_UP); 

        return  volumeML.doubleValue();
    }

    @Override
    public double getCapacityMiliLiters() {
        return calculateNominalCapacity(this.diameterMm, this.heightMm);
    }
}
