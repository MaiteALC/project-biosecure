package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PetriDish extends SampleContainer {
    private int divisionsNumber;
    private boolean grid;
    private boolean ventilated;
    private double diameterMm;
    private double heightMm;
    private double capacityMilliLiters;

    public PetriDish(PetriDishBuilder builder) {
        super(builder);

        this.divisionsNumber = builder.divisionsNumber;
        this.grid = builder.grid;
        this.ventilated = builder.ventilated;
        this.diameterMm = builder.diameterMm;
        this.heightMm = builder.heightMm;
        this.capacityMilliLiters = calculateNominalCapacity(builder.diameterMm, builder.heightMm);
    }

    public static PetriDishBuilder builder() {
        return new PetriDishBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class PetriDishBuilder extends SampleContainerBuilder<PetriDish, PetriDishBuilder> {
        private int divisionsNumber;
        private boolean grid;
        private boolean ventilated;
        private double diameterMm;
        private double heightMm;

        @Override
        protected PetriDishBuilder self() {
            return this;
        }

        @Override
        public PetriDish build() {
            //SampleContainer.validateContainerBioSafetyRules(materialType, getSterilizationMethod());

            NumberUtils.validateNumericalAttribute(heightMm, 1, "height (mm)", 999, productNotification);

            NumberUtils.validateNumericalAttribute(diameterMm, 1, "width (mm)", 999, productNotification);

            NumberUtils.validateNumericalAttribute(divisionsNumber, 1, "divisions number", 4, productNotification);

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            return new PetriDish(this);
        }
    }

    /**
     * Calculates the surface area of a Petri dish per division in quadratic millimeters.
     * <p>
     * The calculation is made using the formula for circle area: {@code A = π * r²} divided by divisions number,
     * considering that 100% of surface is useful.
     * The result is rounded to 2 decimal places.
     * </p>
     * 
     * @param diameter Total diameter in millimeters (mm).
     * Must be between 1 and 999.
     * @param divisionsNum Number of divisions inside the Petri dish.
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
        
        BigDecimal surfaceArea = BigDecimal.valueOf((Math.PI * Math.pow(diameter / 2.0, 2)) / (double) divisionsNum).setScale(2, RoundingMode.HALF_UP);

        return surfaceArea.doubleValue();
    }

    public double getSurfaceAreaPerDivision() {
        return calculateSurfaceAreaPerDiv(this.diameterMm, this.divisionsNumber);
    }

    /**
     * Calculates the nominal capacity (volume) of a Petri Dish in milliliters.
     * <p>
     * Important note: This method calculates considering <strong>useful height of 50%</strong> of given total height
     * considering that the Petri dish is never fully filled.
     * The result is rounded to 2 decimal places.
     * </p>
     * 
     * <p>
     * The calculation uses the formula for the volume of a cylinder.: {@code V = π * r² * h}.
     * </p>
     *
     * @param diameter Total diameter in millimeters (mm).
     * Must be between 1 and 999.
     * @param height   Total height in millimeters (mm).
     * Must be between 1 and 999.
     * @return The calculated capacity in milliliters (mL).
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
}
