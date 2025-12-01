package br.com.biosecure.utils;

import br.com.biosecure.model.product.Sanitizer.ChemicalBase;
import br.com.biosecure.model.product.Sanitizer.ConcentrationUnit;
import java.time.LocalDate;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.Product.PackagingType;
import br.com.biosecure.model.product.Sanitizer.PhysicalForm;
import br.com.biosecure.model.product.Sanitizer;

public class SanitizerBuilder {
    // General attributes of products
    private String name = "Sanitizer Test";
    private double price = 54.90;
    private String manufacturer = "Test Manufacturer";
    private String batchNumber = "Batch-1A";
    private LocalDate expirationDate = LocalDate.of(2027, 3, 3);
    private PackagingType packagingType = PackagingType.BOTTLE;
    private MeasureUnit measureUnit = MeasureUnit.L;
    private double quantityPerPackage = 15.0;

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

    public static SanitizerBuilder aSanitizer() {
        return new SanitizerBuilder();
    }

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

    public Sanitizer build() {
        return new Sanitizer(name, price, activeIngredient, form, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage, registerNumber, useIndications, phLevel, flammable, concentration, concentrationUnit, requiresDilution);
    }
}
