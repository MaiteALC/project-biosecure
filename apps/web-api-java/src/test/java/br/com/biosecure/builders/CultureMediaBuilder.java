package br.com.biosecure.builders;

import java.util.OptionalDouble;
import br.com.biosecure.model.product.CultureMedia;
import br.com.biosecure.model.product.CultureMedia.*;

public class CultureMediaBuilder extends BaseProductBuilder<CultureMediaBuilder, CultureMedia> {
    // Specific attributes of Culture Media
    private CultureMediaFinality finality = CultureMediaFinality.SELECTIVE;
    private PhysicalUnit physicalUnit = PhysicalUnit.PREPARED_LIQUID_PLATE;
    private boolean protectOfLight = true;
    private StorageConditions storageConditions = StorageConditions.FROZEN;
    private OptionalDouble preparationGramsPerLiter = OptionalDouble.of(2);
    private double finalPhLevel = 7;
    private double quantityPerUnit = 5;
    private QuantificationUnit quantificationUnit = QuantificationUnit.ML;

    public static CultureMediaBuilder aCultureMediaBuilder() {
        return new CultureMediaBuilder();
    }
    
    public CultureMediaBuilder withFinality(CultureMediaFinality finality) {
        this.finality = finality;

        return this;
    }

    public CultureMediaBuilder withStorageConditions(StorageConditions storageConditions) {
        this.storageConditions = storageConditions;

        return this;
    }

    public CultureMediaBuilder withPhysicalUnit(PhysicalUnit physicalUnit) {
        this.physicalUnit = physicalUnit;

        return this;
    }

    public CultureMediaBuilder withProtectOfLight(boolean protectOfLight) {
        this.protectOfLight = protectOfLight;

        return this;
    }

    public CultureMediaBuilder withPreparationGramsPerLiter(double preparationGramsPerLiter) {
        this.preparationGramsPerLiter = OptionalDouble.of(preparationGramsPerLiter);

        return this;
    }

    public CultureMediaBuilder withFinalPhLevel(double finalPhLevel) {
        this.finalPhLevel = finalPhLevel;

        return this;
    }

    public CultureMediaBuilder withQuantityPerUnit(double quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;

        return this;
    }

    public CultureMediaBuilder withQuantificationUnit(QuantificationUnit quantificationUnit) {
        this.quantificationUnit = quantificationUnit;

        return this;
    }

    @Override
    protected CultureMediaBuilder self() {
        return this;
    }

    @Override
    public CultureMedia build() {
        return new CultureMedia(name, price, physicalUnit, manufacturer, batchNumber, expirationDate, packagingType,measureUnit, quantityPerPackage, finality, storageConditions, protectOfLight, quantityPerUnit, quantificationUnit, preparationGramsPerLiter.getAsDouble(), finalPhLevel);
    }
}
