package br.com.biosecure.builders;

import br.com.biosecure.model.Ingredient;
import br.com.biosecure.model.Ingredient.ChemicalFamily;

public class IngredientBuilder {
    private String name = "alcohol isopropyl";
    private String casNumber = "67-63-0";
    private ChemicalFamily chemicalFamily =  ChemicalFamily.ALCOHOL;
    private double concentrationPercentual = 99.8;
    private Ingredient.IngredientType function = Ingredient.IngredientType.ACTIVE_INGREDIENT;

    public IngredientBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public IngredientBuilder withCasNumber(String casNumber) {
        this.casNumber = casNumber;
        return this;
    }

    public IngredientBuilder withChemicalBase(ChemicalFamily chemicalFamily) {
        this.chemicalFamily = chemicalFamily;
        return this;
    }

    private void chemicalFam() {
    }

    public IngredientBuilder withConcentrationPercentual(double concentrationPercentual) {
        this.concentrationPercentual = concentrationPercentual;
        return this;
    }

    public IngredientBuilder withFunction(Ingredient.IngredientType function) {
        this.function = function;
        return this;
    }

    public static IngredientBuilder anActiveIngredient() {
        return new IngredientBuilder();
    }

    public static Ingredient buildSodiumHypochlorite() {
        return new Ingredient("Sodium hypochlorite", "7681-52-9", ChemicalFamily.CHLORINE_RELEASING_AGENT, 1, Ingredient.IngredientType.ACTIVE_INGREDIENT);
    }

    public static Ingredient buildEthanol() {
        return new Ingredient("Ethanol", "64-17-5", ChemicalFamily.ALCOHOL, 70,  Ingredient.IngredientType.ACTIVE_INGREDIENT);
    }

    public Ingredient build() {
        return new Ingredient(name, casNumber, chemicalFamily, concentrationPercentual, function);
    }
}
