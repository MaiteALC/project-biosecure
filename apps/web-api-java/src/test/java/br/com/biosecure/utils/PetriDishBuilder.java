package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.PetriDish;
import br.com.biosecure.model.product.Product.PackagingType;
import br.com.biosecure.model.product.SampleContainer.*;

public class PetriDishBuilder {
    // General attributes of products
    private String name = "Sanitizer Test";
    private double price = 54.90;
    private String manufacturer = "Test Manufacturer";
    private String batchNumber = "Batch-1A";
    private LocalDate expirationDate = LocalDate.of(2027, 3, 3);
    private PackagingType packagingType = PackagingType.BOX;
    private int quantityPerPackage = 15;

    // Attributes of super class Sample Container
    private ClosingMethod closingMethod = ClosingMethod.LID_OVERLAY;
    private SterilizationMethod sterilizationMethod = SterilizationMethod.GAMMA_RAYS;
    private Material material = Material.PS;

    // Specific attributes of Petri Dish
    private int divNum;
    private boolean grid;
    private boolean ventilated;
    private double diameterMm;
    private double heightMm;

    public PetriDishBuilder withClosingMethod(ClosingMethod closingMethod) {
        this.closingMethod = closingMethod;

        return this;
    }

    public PetriDishBuilder withSterilizationMethod(SterilizationMethod sterilizationMethod) {
        this.sterilizationMethod = sterilizationMethod;

        return this;
    }

    public PetriDishBuilder withMaterial(Material material) {
        this.material = material;

        return this;
    }

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

    public PetriDishBuilder withDiameterMm(double diameterMm) {
        this.diameterMm = diameterMm;

        return this;
    }

    public PetriDishBuilder withHeightMm(double heightMm) {
        this.heightMm = heightMm;

        return this;
    }

    public static PetriDishBuilder aPetriDish() {
        return new PetriDishBuilder();
    }

    public PetriDish build() {
        return new PetriDish(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, material, divNum, grid, ventilated, diameterMm, heightMm);
    }
}
