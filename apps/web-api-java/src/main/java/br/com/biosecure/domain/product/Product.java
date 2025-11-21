package br.com.biosecure.domain.product;

import java.time.LocalDate;

public abstract class Product {
    private final String name;
    private double price;
    private SKU sku;
    private final String manufacturer;
    private final String batchNumber;
    private final LocalDate expirationDate;
    private final PackagingType packagingType;
    private final MeasureUnity measureUnity;
    private final double qtdPerPackage;

    public Product(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnity measureUnity, int qtdPerPackage) {
        validateString(name, "name");
        validateString(manufacturer, "manufacturer");
        validateString(batchNumber, "batch number");

        if (qtdPerPackage < 1.0) {
            throw new InvalidProductAttributeException("quantity per package");
        }

        if (price < 1.0) {
            throw new InvalidProductAttributeException("price");
        }

        if (LocalDate.now().isAfter(expirationDate)) {
            throw new InvalidProductAttributeException("expiration date");
        }

        if (packagingType == PackagingType.INDIVIDUAL && qtdPerPackage != 1.0) {
            throw new InvalidProductAttributeException("quantity per package and packaging type has incoherent");
        }

        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.batchNumber = batchNumber;
        this.expirationDate = expirationDate;
        this.packagingType = packagingType;
        this.measureUnity = measureUnity;
        this.qtdPerPackage = qtdPerPackage;
    }

    public enum MeasureUnity {
        ML,
        L,
        KG,
        G,
        UN, 
        PAIR
    }

    public enum PackagingType {
        BOX("B"),
        PACKAGE("P"),
        BOTTLE("T"),
        GALLON("G"),
        INDIVIDUAL("I");

        private final String code;

        PackagingType(String code) {
            this.code = code;
        }

        public String getPackagingTypeCode() {
            return code;
        }
    }

    private void validateString(String value, String attributeName) {
        if (value == null || value.trim().isBlank()) {
            throw new InvalidProductAttributeException(attributeName);
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public PackagingType getPackagingType() {
        return packagingType;
    }

    public MeasureUnity getMeasureUnity() {
        return measureUnity;
    }

    public double getQtdPerPackage() {
        return qtdPerPackage;
    }

    public SKU getSku() {
        // Lazy initialization of SKU
        if (this.sku == null) {
            this.sku = new SKU(this);
        }

        return sku;
    }

    public void setPrice(double newPrice) {
        if (newPrice < 1.0) {
            throw new InvalidProductAttributeException("price");
        }

        this.price = newPrice;
    }

    @Override
    public String toString() {
        return "Product [name:" + name + ", price:" + price + ", sku:" + sku + ", manufacturer:" + manufacturer + ", batchNumber:" + batchNumber + ", expirationDate:" + expirationDate + ", qtdPerPackage:" + qtdPerPackage + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product obj = (Product) o;

        return sku.equals(obj.sku) && manufacturer.equals(obj.manufacturer) && name.equals(obj.name);
    }
}