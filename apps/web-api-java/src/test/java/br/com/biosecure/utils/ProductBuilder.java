package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.Product;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.Product.PackagingType;

public class ProductBuilder {
    private String name = "Test Product";
    private double price = 54.9;
    private String manufacturer = "Generic Manufacturer";
    private String batchNumber = "Batch-A1";
    private LocalDate expirationDate = LocalDate.of(2027, 6, 12);
    private PackagingType packagingType = PackagingType.PACKAGE;
    private MeasureUnit measureUnit = MeasureUnit.KG;
    private double quantityPerPackage = 20;

    private static class ProductDummy extends Product {
        public ProductDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage) {    
            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);
        }
    }

    public ProductBuilder withName(String name) {
        this.name = name;

        return this;
    }

    public ProductBuilder withPrice(double price) {
        this.price = price;

        return this;
    }

    public ProductBuilder withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;

        return this;
    }

    public ProductBuilder withBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;

        return this;
    }

    public ProductBuilder withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;

        return this;
    }

    public ProductBuilder withPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;

        return this;
    }

    public ProductBuilder withMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;

        return this;
    }

    public ProductBuilder withQuantityPerPackage(double quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;

        return this;
    }

    public Product build() {
        return new ProductDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder(); 
    }
}
