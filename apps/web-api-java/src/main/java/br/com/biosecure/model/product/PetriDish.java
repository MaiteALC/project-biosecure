package br.com.biosecure.model.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;

public class PetriDish extends SampleContainer {
    private final int divNum;
    private final boolean grid;
    private final boolean ventilated;
    private final double diameterMm;
    private final double heightMm;

    public PetriDish(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, SterilizationMethod sterilizationMethod, ClosingMethod closingMethod, Material materialType, int divisionsNumber, boolean hasGrid, boolean isVentilated, double diameterMm, double heightMm) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, materialType);

        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(heightMm, 1, "heigth (mm)", 999, notification);
        NumberUtils.validateNumericalAttribute(diameterMm, 1, "width (mm)", 999, notification);

        NumberUtils.validateNumericalAttribute(divisionsNumber, 1, "divisions number", 4, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
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

    /**
     * Calculates the surface area of a petri dish per division in quadratic milimeters.
     * <p>
     * The calculation is made using the formula for circle area: {@code A = π * r²} divided by divisions number,
     * considering that 100% of surface is usefull.
     * The result is rounded to 2 decimal places.
     * </p>
     * 
     * @param diameter Total diameter in milimeters (mm).
     * Must be betweeen 1 and 999.
     * @param divNum Number of divisions inside the petri dish.
     * Must be between 1 and 4.
     * @return The calculated surface area per division in mm².
     * @throws InvalidProductAttributeException if the diameter or divNum is shorter than 1, if divNum greater than 4 or diameter greater than 999.
     */
    public static double calculateSurfaceAreaPerDiv(double diameter, int divisionsNum) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(divisionsNum, 1,"divisions number", 4, notification);

        NumberUtils.validateNumericalAttribute(diameter, 1, "diameter (mm)", 999, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }
        
        BigDecimal surfeaceArea = BigDecimal.valueOf((Math.PI * Math.pow(diameter / 2.0, 2)) / (double) divisionsNum).setScale(2, RoundingMode.HALF_UP); 

        return surfeaceArea.doubleValue();
    }

    public double getSurfaceAreaPerDivison() {
        return calculateSurfaceAreaPerDiv(this.diameterMm, this.divNum);
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
     * Must be betweeen 1 and 999.
     * @param height   Total height in milimeters (mm).
     * Must be between 1 and 999.
     * @return The calculated capacity in mililiters (mL).
     * @throws InvalidProductAttributeException If dimension is out of allowed limits (is <1 or >999).
     */
    public static double calculateNominalCapacity(double diameter, double height) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(diameter, 1, "diameter (mm)", 999, notification);
        NumberUtils.validateNumericalAttribute(height, 1, "height (mm)", 999, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        double usefulHeight = height / 2.0;

        double volumeCubicMm = Math.PI * Math.pow(diameter / 2.0, 2) * usefulHeight;

        BigDecimal volumeML =  BigDecimal.valueOf(volumeCubicMm / 1000).setScale(2, RoundingMode.HALF_UP); 

        return  volumeML.doubleValue();
    }

    public double getCapacityMiliLiters() {
        return calculateNominalCapacity(this.diameterMm, this.heightMm);
    }
}
