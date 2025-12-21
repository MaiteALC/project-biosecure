package br.com.biosecure.model.product;

import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;

public abstract class Product {
    private final String name;
    private double price;
    private SKU sku;
    private final String manufacturer;
    private final String batchNumber;
    private final LocalDate expirationDate;
    private final PackagingType packagingType;
    private final MeasureUnit measureUnit;
    private final double quantityPerPackage;

    protected static final int MIN_NAMES_LENGTH = 2;
    protected static final int MAX_NAMES_LENGTH = 70;

    protected static final double MAX_PRICE = 99999.99;
    protected static final double MAX_QUANTITY = 99999;

    public Product(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage) {
        
        NotificationContext productNotification = new NotificationContext();
        
        StringUtils.validateString(name, MIN_NAMES_LENGTH, "name", MAX_NAMES_LENGTH, productNotification);
        StringUtils.validateString(manufacturer, MIN_NAMES_LENGTH, "manufacturer", MAX_NAMES_LENGTH, productNotification);
        StringUtils.validateString(batchNumber, "batch number", productNotification);

        NumberUtils.validateNumericalAttribute(price, 0.01, "price", MAX_PRICE, productNotification);
        NumberUtils.validateNumericalAttribute(quantityPerPackage, 1, "quantity per package", MAX_QUANTITY, productNotification);
        NumberUtils.validateExpirationDate(expirationDate, "expiration date", productNotification);
        
        if (packagingType == PackagingType.INDIVIDUAL && measureUnit != MeasureUnit.U) {
            productNotification.addError("measure unit", "The measure unit and packaging type are incoherent.");;
        }
        
        if (productNotification.hasErrors()) {
            throw new InvalidProductAttributeException(productNotification.getErrors());
        }

        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.batchNumber = batchNumber;
        this.expirationDate = expirationDate;
        this.packagingType = packagingType;
        this.measureUnit = measureUnit;
        this.quantityPerPackage = packagingType == PackagingType.INDIVIDUAL ? 1 : quantityPerPackage;
    }

    public enum MeasureUnit {
        ML,
        L,
        KG,
        G,
        MG,
        U,
        MM
    }

    public enum PackagingType {
        BOX("BX"),
        PACKAGE("P"),
        BOTTLE("BT"),
        GALLON("G"),
        INDIVIDUAL("INDV");

        private final String code;

        PackagingType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
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

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public double getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public SKU getSku() {
        // Lazy initialization of SKU
        if (this.sku == null) {
            this.sku = new SKU(this);
        }

        return sku;
    }

    public void setPrice(double newPrice) {
        NotificationContext productNotification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(price, 0.01, "price", MAX_PRICE, productNotification);

        if (productNotification.hasErrors()) {
            throw new InvalidProductAttributeException(productNotification.getErrors());
        }

        this.price = newPrice;
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

        return this.getSku().equals(obj.getSku()) && manufacturer.equals(obj.manufacturer) && name.equals(obj.name);
    }
}