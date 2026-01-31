package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.ErrorAggregator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestTube extends SampleContainer {
    private int maxRCF;
    @Enumerated(EnumType.STRING)
    private BottomType bottomType;
    private boolean graduated;
    @Enumerated(EnumType.STRING)
    private CapColor capColor;
    private double diameterMm;
    private double heightMm;
    private double capacityMilliLiters;

    public TestTube(TestTubeBuilder builder) {
        super(builder);

        validateTestTubeBioSafetRules(super.getMaterialType(), builder.bottomType, builder.maxRCF);

        this.maxRCF = builder.maxRCF;
        this.bottomType = builder.bottomType;
        this.graduated = builder.graduated;
        this.capColor = builder.capColor;
        this.diameterMm = builder.diameterMm;
        this.heightMm = builder.heightMm;
        this.capacityMilliLiters = calculateNominalCapacity(builder.diameterMm, builder.heightMm);
    }

    public static TestTubeBuilder builder() {
        return new TestTubeBuilder();
    }

    @Setter
    @Accessors(fluent = true,  chain = true)
    public static final class TestTubeBuilder extends SampleContainerBuilder<TestTube, TestTubeBuilder> {
        private int maxRCF;
        private BottomType bottomType;
        private boolean graduated;
        private CapColor capColor;
        private double diameterMm;
        private double heightMm;

        @Override
        protected TestTubeBuilder self() {
            return this;
        }

        @Override
        public TestTube build() {
            ErrorAggregator.aggregateValidationExceptions(
                    List.of(
                            ErrorAggregator.verifyNull(capColor, "cap color"),
                            ErrorAggregator.verifyNull(bottomType, "bottom type")
                    ),
                    productNotification
            );

            NumberUtils.validateNumericalAttribute(heightMm, 1, "height (mm)", 999, productNotification);

            NumberUtils.validateNumericalAttribute(diameterMm, 1, "diameter (mm)", 999, productNotification);

            NumberUtils.validateNumericalAttribute(maxRCF, 1, "maximum RCF", 500000, productNotification);

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            validateTestTubeBioSafetRules(this.materialType, this.bottomType, this.maxRCF);

            return new TestTube(this);
        }
    }

    public enum BottomType {
        CONICAL,
        FLAT,
        ROUND,
        SKIRTED
    }

    @Getter
    @AllArgsConstructor
    public enum CapColor {
        BLUE("Sodium citrate"),
        GRAY("Potassium fluoride and EDTA"),
        GREEN("Heparin"),
        PURPLE("EDTA"),
        RED("Clot activator"),
        WHITE("None"),
        YELLOW("Clot activator and separator gel");

        private final String chemicalAdditive;
    }

    private static void validateTestTubeBioSafetRules(Material material, BottomType bottomType, int maxRCF) {
        ArrayList<String> invalids = new ArrayList<>();

        invalids.add("Maximum RCF");

        if (material == Material.BOROSILICATE_GLASS && maxRCF > 5000) {
            invalids.add("Material");

            throw new BioSecurityException(
                "Glass tubes rarely supports RCF greater than 5000g. Verify the specifications.", invalids
            );
        }

        if (bottomType == BottomType.FLAT && maxRCF > 10000) {
            invalids.add("Bottom type");

            throw new BioSecurityException(
                "Flat bottoms concentrate the tension. RCF greater than 10.000g is uncommon for flat bottoms. Verify the specifications", invalids
            );
        }
    }

    /**
     * Calculates the nominal capacity (volume) of a Test Tube in milliliters.
     * <p>
     * The calculation uses the formula for the volume of a cylinder.: {@code V = π * r² * h}.
     * </p>
     *
     * @param diameter Total diameter in millimeters (mm).
     * Must be between 1 and 999.
     * @param height   Total height in millimeters (mm).
     * Must be between 1 and 999.
     * @return The calculated capacity in milliliters (mL).
     * @throws InvalidProductAttributeException If dimension is out of allowed limits (<1 or >999).
     */    
    public static double calculateNominalCapacity(double diameter, double height) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(diameter, 1, "diameter (mm)", 999, notification);
        NumberUtils.validateNumericalAttribute(height, 1, "height (mm)", 999, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        double volumeCubicMm = Math.PI * Math.pow(diameter / 2.0, 2) * height;

        BigDecimal volumeML =  BigDecimal.valueOf(volumeCubicMm / 1000).setScale(2, RoundingMode.HALF_UP); 

        return  volumeML.doubleValue();
    }
}
