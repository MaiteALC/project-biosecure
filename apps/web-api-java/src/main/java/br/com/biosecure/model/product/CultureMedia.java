package br.com.biosecure.model.product;

import java.util.ArrayList;
import java.util.OptionalDouble;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import java.time.LocalDate;

public class CultureMedia extends Product {
    private final CultureMediaFinality finality;
    private final StorageConditions storageConditions;
    private final Presentation presentation;
    private final boolean protectOfLight;
    private final OptionalDouble preparationGramsPerLiter;
    private final double finalPhLevel;
    private final double quantityPerUnit;
    private final QuantificationUnit quantificationUnit;

    public CultureMedia(String name, double price, Presentation presentation, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage, CultureMediaFinality finality, StorageConditions storageConditions, boolean protectOfLight, double quantityPerUnit, QuantificationUnit quantificationUnit, OptionalDouble preparationGramsPerLiter, double finalPhLevel) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);

        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(finalPhLevel, 0, "final pH level", 14, notification);

        if (presentation.isRTU() != quantificationUnit.isToRTUProduct()) {
            notification.addError("quantification unit/presentation form",
            "There is a incoherence among the quantification unit, physical unit and if the product is ready to use. Both units must be compatible with 'is ready to use' attribute.");
        }

        validatePreparationQuantity(preparationGramsPerLiter, presentation, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        validateBioSafetyRules(presentation, storageConditions);

        this.finality = finality;
        this.storageConditions = storageConditions;
        this.presentation = presentation;
        this.protectOfLight = protectOfLight;
        this.quantityPerUnit = quantityPerUnit;
        this.quantificationUnit = quantificationUnit;
        this.preparationGramsPerLiter = preparationGramsPerLiter;
        this.finalPhLevel = finalPhLevel;
    } 

    private OptionalDouble validatePreparationQuantity(OptionalDouble quantity, Presentation presentation, NotificationContext notification) {
        if (presentation.isRTU()) {
            return OptionalDouble.empty();
        }
            
        else {
            if (quantity.isEmpty() || quantity == null) {
                notification.addError("quantity for preparation (g/L)",
                    "If the product isn't RTU a value for preparation quantity (g/L) must be provided.");
                
                return OptionalDouble.empty();
            }

            else {
                NumberUtils.validateNumericalAttribute(quantity.getAsDouble(), 0.001, "quantity for preparation (g/L)", 999, notification);

                return OptionalDouble.empty();
            }
        }
    }

    private void validateBioSafetyRules(Presentation form, StorageConditions storageTemp) {
        ArrayList<String> invalids = new ArrayList<>();

        invalids.add("Storage conditions");
        invalids.add("Physical unit");

        if (form.isRTU() && (storageTemp == StorageConditions.AMBIENT_TEMP || storageTemp == StorageConditions.FRESH) ) {
            throw new BioSecurityException(
                "Preparated plates/tubes/bottles requires refrigeration (8°C or less) to don't contaminate or dry out.", invalids
            );
        }

        if (!form.isRTU() && (storageTemp != StorageConditions.AMBIENT_TEMP && storageTemp != StorageConditions.FRESH) ) {
            throw new BioSecurityException(
                "Dehydrated powder requires ambient temperature to don't compromise effectiveness.", invalids
            );
        }
    }

    public enum CultureMediaFinality {
        SELECTIVE("Don't kills some microorganisms (the selects/desirable) and kills the others"),
        DIFFERENTIAL("Used to differentiate the microorganisms in the sample (using ph indicators or dyes, e.g"),
        TRANSPORT("Used to conservative the sample, not to stimulate microorganisms grows"),
        ENRICHMENT("Stimulates the growth of desired microorganisms that use to be in less quantity"),
        CHROMOGENIC("Uses chromogens to give color to microorganisms that contain certain enzymes"),
        SIMPLE("For general purposes");

        private final String label;

        CultureMediaFinality(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum Presentation {
        DEHYDRATED_POWDER_BOTTLE(false),
        DEHYDRATED_POWDER_SACHET(false),
        PREPARED_LIQUID_BOTTLE(true),
        PREPARED_LIQUID_TUBE(true),
        PREPARED_LIQUID_PLATE(true);

        private final boolean isRTU;

        Presentation(boolean isRTU) {
            this.isRTU = isRTU;
        }

        public boolean isRTU() {
            return isRTU;
        }
    }

    public enum StorageConditions {
        AMBIENT_TEMP(15, 30, "AMB"),
        FRESH(8, 15, "FRE"),
        REFRIGERATED(2, 8, "REF"),
        FROZEN(-20, 0, "FRO"),
        ULTRA_FREEZER(-150, -20, "ULT");

        private final int minTemp;
        private final int maxTemp;
        private final String code;

        StorageConditions(int minTemperature, int maxTemperature, String code) {
            this.minTemp = minTemperature;
            this.maxTemp = maxTemperature;
            this.code = code;
        }

        public int getMinTemperature() {
            return  minTemp;
        }

        public int getMaxTemperature() {
            return maxTemp;
        }

        public String getCode() {
            return code;
        }

        public boolean requiresColdChain() {
            return this == REFRIGERATED || this == FROZEN || this == ULTRA_FREEZER;
        }
    }

    public enum QuantificationUnit {
        ML(true),
        L(true),
        MG(false),
        G(false),
        KG(false);

        private final boolean toRTUProduct;

        QuantificationUnit(boolean toRTUProduct) {
            this.toRTUProduct = toRTUProduct;
        }

        private boolean isToRTUProduct() {
            return toRTUProduct;
        }
    }

    public CultureMediaFinality getFinality() {
        return finality;
    }

    public StorageConditions getStorageConditions() {
        return storageConditions;
    }

    public Presentation getPresentationForm() {
        return presentation;
    }

    public  boolean isProtectOfLight() {
        return protectOfLight;
    }

    public double getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public QuantificationUnit getQuantityUnit() {
        return quantificationUnit;
    }

    public double getFinalPhLevel() {
        return finalPhLevel;
    }

    public OptionalDouble getPreparationGramsPerLiter() {
        return preparationGramsPerLiter;
    }
}
