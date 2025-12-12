package br.com.biosecure.builders;

import br.com.biosecure.model.product.PetriDish;

public class PetriDishBuilder extends BaseSampleContainerBuilder<PetriDishBuilder, PetriDish> {
    // Specific attributes of Petri Dish
    private int divNum = 2;
    private boolean grid = false;
    private boolean ventilated = true;
    private double diameterMm = 90;
    private double heightMm = 15;

    public PetriDishBuilder withDivNum(int divNum) {
        this.divNum = divNum;

        return this;
    }

    public PetriDishBuilder withGrid(boolean grid) {
        this.grid = grid;

        return this;
    }

    public PetriDishBuilder withVentilated(boolean ventilated) {
        this.ventilated = ventilated;

        return this;
    }

    public PetriDishBuilder withDiameterMm(int diameter) {
        this.diameterMm = diameter;

        return this;
    }

    public PetriDishBuilder withHeightMm(int height) {
        this.heightMm = height;

        return this;
    }
    
    public PetriDishBuilder withDiameterMm(double diameter) {
        this.diameterMm = diameter;

        return this;
    }

    public PetriDishBuilder withHeightMm(double height) {
        this.heightMm = height;

        return this;
    }

    @Override
    protected PetriDishBuilder self() {
        return this;
    }

    public static PetriDishBuilder aPetriDish() {
        return new PetriDishBuilder();
    }

    @Override
    public PetriDish build() {
        return new PetriDish(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, sterilizationMethod, closingMethod, material, divNum, grid, ventilated, diameterMm, heightMm);
    }
}
