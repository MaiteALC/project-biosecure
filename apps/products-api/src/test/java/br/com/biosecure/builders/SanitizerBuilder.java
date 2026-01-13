package br.com.biosecure.builders;

import br.com.biosecure.model.Ingredient;
import br.com.biosecure.model.Ingredient.ChemicalFamily;
import br.com.biosecure.model.Sanitizer.*;
import br.com.biosecure.model.Sanitizer;
import java.util.List;

public class SanitizerBuilder extends BaseProductBuilder<SanitizerBuilder, Sanitizer> {
    // Specifics attributes of Sanitizer
    private List<Ingredient> ingredients = List.of(
            new Ingredient("Alkyl Dimethyl Benzyl Ammonium Chloride", "68424-85-1", ChemicalFamily.QUATERNARY_AMMONIUM, 50, Ingredient.IngredientType.ACTIVE_INGREDIENT),
            new Ingredient("Didecyl Dimethyl Ammonium Chloride", "7173-51-5", ChemicalFamily.QUATERNARY_AMMONIUM, 50, Ingredient.IngredientType.ACTIVE_INGREDIENT)
    );
    private PhysicalForm form = PhysicalForm.LIQUID;
    private String registryNumber = "123-Test-Anv";
    private String useIndications = "Test test test test test";
    private double phLevel = 7;
    private boolean flammable = false;
    private boolean requiresDilution = false;
    private double density = 1;
    private ChemicalFamily mainChemicalFamily = ChemicalFamily.QUATERNARY_AMMONIUM;

    public SanitizerBuilder withActiveIngredient(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
         
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

    public SanitizerBuilder withRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;

        return this;
    }

    public  SanitizerBuilder withUseIndications(String useIndications) {
        this.useIndications = useIndications;

        return this;
    }

    public SanitizerBuilder withMainChemicalFamily(ChemicalFamily mainChemicalFamily) {
        this.mainChemicalFamily = mainChemicalFamily;

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
        return new Sanitizer(name, price, ingredients, form, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage, registryNumber, useIndications, phLevel, requiresDilution, density, flammable, mainChemicalFamily);
    }
}
