package br.com.biosecure.utils;

import br.com.biosecure.model.product.Sanitizer.ChemicalBase;
import br.com.biosecure.model.product.Sanitizer.ConcentrationUnit;
import br.com.biosecure.model.product.Sanitizer.PhysicalForm;
import br.com.biosecure.model.product.Sanitizer;

public class SanitizerBuilder extends ProductBuilder<SanitizerBuilder, Sanitizer> {
    // Specifics attributes of Sanitizer
    private ChemicalBase activeIngredient = ChemicalBase.QUATERNARY_AMMONIUM;
    private PhysicalForm form = PhysicalForm.LIQUID;
    private String registerNumber = "123-Test-Anvisa";
    private String useIndications = "Test test test test test";
    private double phLevel = 7;
    private double concentration = 1;
    private ConcentrationUnit concentrationUnit = ConcentrationUnit.PERCENTAGE;
    private boolean flammable = false;
    private boolean requiresDilution = false;
    private double density = 1;

    public SanitizerBuilder withActiveIngredient(ChemicalBase chemicalBase) {
        this.activeIngredient = chemicalBase;
         
        return this;
    }

    public SanitizerBuilder withForm(PhysicalForm form) {
        this.form = form;

        return this;
    }

    public SanitizerBuilder withPhLevel(double phLevel) {
        this.phLevel = phLevel;

        return this;
    }

    public SanitizerBuilder withConcentration(double concentration) {
        this.concentration = concentration;

        return this;
    }

    public SanitizerBuilder withConcentrationUnit(ConcentrationUnit concentrationUnit) {
        this.concentrationUnit = concentrationUnit;

        return this;
    }

    public SanitizerBuilder withFlammable(boolean flammable) {
        this.flammable = flammable;

        return this;
    }

    public SanitizerBuilder withRequiresDilution(boolean requiresDilution) {
        this.requiresDilution = requiresDilution;

        return this;
    }

    public SanitizerBuilder withDensity(double density) {
        this.density = density;

        return this;
    }

    public static SanitizerBuilder aSanitizer() {
        return new SanitizerBuilder();
    }

    @Override
    protected SanitizerBuilder self() {
        return this;
    }

    @Override
    public Sanitizer build() {
        return new Sanitizer(name, price, activeIngredient, form, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage, registerNumber, useIndications, phLevel, flammable, concentration, concentrationUnit, requiresDilution, density);
    }
}
