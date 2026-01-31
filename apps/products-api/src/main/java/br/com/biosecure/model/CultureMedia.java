package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.ErrorAggregator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CultureMedia extends Product {
    @Enumerated(EnumType.STRING)
    private CultureMediaFinality finality;
    @Enumerated(EnumType.STRING)
    private StorageConditions storageConditions;
    @Enumerated(EnumType.STRING)
    private Presentation presentation;
    private boolean protectOfLight;
    private OptionalDouble preparationGramsPerLiter;
    private double finalPhLevel;
    private double quantityPerUnit;
    @Enumerated(EnumType.STRING)
    private QuantificationUnit quantificationUnit;

    private CultureMedia(CultureMediaBuilder builder) {
        super(builder);

        this.finality = builder.cultureMediaFinality;
        this.storageConditions = builder.storageConditions;
        this.presentation = builder.presentation;
        this.protectOfLight = builder.protectOfLight;
        this.preparationGramsPerLiter = builder.preparationGramsPerLiter;
        this.finalPhLevel = builder.finalPhLevel;
        this.quantityPerUnit = builder.quantityPerUnit;
        this.quantificationUnit = builder.quantificationUnit;
    }

    public static CultureMediaBuilder builder() {
        return new CultureMediaBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static class CultureMediaBuilder extends ProductBuilder<CultureMedia, CultureMediaBuilder> {
        private CultureMediaFinality cultureMediaFinality;
        private StorageConditions storageConditions;
        private Presentation presentation;
        private boolean protectOfLight;
        private OptionalDouble preparationGramsPerLiter;
        private double finalPhLevel;
        private double quantityPerUnit;
        private QuantificationUnit quantificationUnit;

        @Override
        public CultureMediaBuilder self() {
            return this;
        }

        @Override
        public CultureMedia build() {
            ErrorAggregator.aggregateValidationExceptions(
                    List.of(
                            ErrorAggregator.verifyNull(storageConditions, "storageConditions"),
                            ErrorAggregator.verifyNull(cultureMediaFinality, "culture media finality"),
                            ErrorAggregator.verifyNull(quantificationUnit, "quantification unit")
                    ),
                    productNotification
            );

            NumberUtils.validateNumericalAttribute(finalPhLevel, 0, "final pH level", 14, productNotification);

            if (presentation.isRTU() != quantificationUnit.isToRTUProduct()) {
                productNotification.addError("quantification unit/presentation form",
                        "There is a incoherence among the quantification unit, physical unit and if the product is ready to use. Both units must be compatible with 'is ready to use' attribute.");
            }

            if (quantityPerUnit < 1) {
                productNotification.addError("quantity per unit", "quantity per unit mustn't be zero or  negative");
            }

            validatePreparationQuantity(preparationGramsPerLiter, presentation, productNotification);

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            validateBioSafetyRules(presentation, storageConditions);

            return new CultureMedia(this);
        }
    }

    private static void validatePreparationQuantity(OptionalDouble quantity, Presentation presentation, NotificationContext notification) {
        if (!presentation.isRTU()) {
            if (quantity.isEmpty()) {
                notification.addError("quantity for preparation (g/L)",
                        "If the product isn't RTU a value for preparation quantity (g/L) must be provided.");
            } else {
                NumberUtils.validateNumericalAttribute(quantity.getAsDouble(), 0.001, "quantity for preparation (g/L)", 999, notification);
            }
        }
    }

    private static void validateBioSafetyRules(Presentation form, StorageConditions storageTemp) {
        ArrayList<String> invalids = new ArrayList<>();

        invalids.add("Storage conditions");
        invalids.add("Physical unit");

        if (form.isRTU() && (storageTemp == StorageConditions.AMBIENT_TEMP || storageTemp == StorageConditions.FRESH)) {
            throw new BioSecurityException(
                    "Preparated plates/tubes/bottles requires refrigeration (8°C or less) to don't contaminate or dry out.", invalids
            );
        }

        if (!form.isRTU() && (storageTemp != StorageConditions.AMBIENT_TEMP && storageTemp != StorageConditions.FRESH)) {
            throw new BioSecurityException(
                    "Dehydrated powder requires ambient temperature to don't compromise effectiveness.", invalids
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CultureMediaFinality {
        SELECTIVE("Don't kills some microorganisms (the selects/desirable) and kills the others"),
        DIFFERENTIAL("Used to differentiate the microorganisms in the sample (using ph indicators or dyes, e.g"),
        TRANSPORT("Used to conservative the sample, not to stimulate microorganisms grows"),
        ENRICHMENT("Stimulates the growth of desired microorganisms that use to be in less quantity"),
        CHROMOGENIC("Uses chromogens to give color to microorganisms that contain certain enzymes"),
        SIMPLE("For general purposes");

        private final String label;
    }

    @Getter
    @AllArgsConstructor
    public enum Presentation {
        DEHYDRATED_POWDER_BOTTLE(false),
        DEHYDRATED_POWDER_SACHET(false),
        PREPARED_LIQUID_BOTTLE(true),
        PREPARED_LIQUID_TUBE(true),
        PREPARED_LIQUID_PLATE(true);

        private final boolean isRTU;
    }

    @Getter
    @AllArgsConstructor
    public enum StorageConditions {
        AMBIENT_TEMP(15, 30, "AMB"),
        FRESH(8, 15, "FRE"),
        REFRIGERATED(2, 8, "REF"),
        FROZEN(-20, 0, "FRO"),
        ULTRA_FREEZER(-150, -20, "ULT");

        private final int minTemp;
        private final int maxTemp;
        private final String code;
    }

    @Getter
    @AllArgsConstructor
    public enum QuantificationUnit {
        ML(true),
        L(true),
        MG(false),
        G(false),
        KG(false);

        private final boolean toRTUProduct;
    }
}