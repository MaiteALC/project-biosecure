package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.SampleBag.*;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.model.product.SampleBag;
import br.com.biosecure.model.product.Product.PackagingType;

public class SampleBagBuilder {
    // General attributes of products
    private String name = "Sanitizer Test";
    private double price = 54.90;
    private String manufacturer = "Test Manufacturer";
    private String batchNumber = "Batch-1A";
    private LocalDate expirationDate = LocalDate.of(2027, 2, 11);
    private PackagingType packagingType = PackagingType.BOX;
    private int quantityPerPackage = 15;

    // Attributes of super class Sample Container
    private ClosingMethod closingMethod = ClosingMethod.ZIP_LOCK;
    private SterilizationMethod sterilizationMethod = SterilizationMethod.GAMMA_RAYS;
    private Material material = Material.PS;
    private double capacityMiliLiters = 300;

    // Specific attributes of Sample Bag
    private FilterType filter = FilterType.FULL_PAGE;
    private boolean identificationTag = true;
    private boolean standUp = false;
    private double thicknessMm = 3;
    private double widthMm = 10;
    private double heigthMm = 22;

    public SampleBagBuilder withFilter(FilterType filter) {
        this.filter = filter;

        return this;
    }

    public SampleBagBuilder withIdentificationTag(boolean identificationTag) {
        this.identificationTag = identificationTag;

        return this;
    }

    public SampleBagBuilder withStandUp(boolean standUp) {
        this.standUp = standUp;

        return this;
    }

    public SampleBagBuilder withThicknessMm(double thicknessMm) {
        this.thicknessMm = thicknessMm;

        return this;
    }

    public SampleBagBuilder withWidthMm(double widthMm) {
        this.widthMm = widthMm;

        return this;
    }

    public SampleBagBuilder withHeigthMm(double heigthMm) {
        this.heigthMm = heigthMm;

        return this;
    }

    public SampleBagBuilder withClosingMethod(ClosingMethod closingMethod) {
        this.closingMethod = closingMethod;

        return this;
    }

    public SampleBagBuilder withSterilizationMethod(SterilizationMethod sterilizationMethod) {
        this.sterilizationMethod = sterilizationMethod;

        return this;
    }

    public SampleBagBuilder withMaterial(Material material) {
        this.material = material;

        return this;
    }

    public SampleBagBuilder withCapacityMiliLiters(double capacityMiliLiters) {
        this.capacityMiliLiters = capacityMiliLiters;

        return this;
    }

    public static SampleBagBuilder aSampleBag() {
        return new SampleBagBuilder();
    }

    public SampleBag build() {
        return new SampleBag(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, material, filter, identificationTag, standUp, thicknessMm, capacityMiliLiters, widthMm, heigthMm);
    }
}
