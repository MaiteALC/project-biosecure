package br.com.biosecure.builders;

import java.time.LocalDate;
import br.com.biosecure.model.product.Product;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.Product.PackagingType;

abstract class BaseProductBuilder<T extends BaseProductBuilder<T, P>, P extends Product> {
    protected String name = "Test Product";
    protected double price = 54.9;
    protected String manufacturer = "Generic Manufacturer";
    protected String batchNumber = "Batch-A1";
    protected LocalDate expirationDate = LocalDate.of(2027, 6, 12);
    protected PackagingType packagingType = PackagingType.PACKAGE;
    protected MeasureUnit measureUnit = MeasureUnit.KG;
    protected double quantityPerPackage = 20;

    protected abstract T self();

    public abstract P build();

    public T withName(String name) {
        this.name = name;

        return self();
    }

    public T withPrice(double price) {
        this.price = price;

        return self();
    }

    public T withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;

        return self();
    }

    public T withBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;

        return self();
    }

    public T withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;

        return self();
    }

    public T withPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;

        return self();
    }

    public T withMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;

        return self();
    }

    public T withQuantityPerPackage(double quantity) {
        this.quantityPerPackage = quantity;

        return self();
    }
    
    public T withQuantityPerPackage(int quantity) {
        this.quantityPerPackage = quantity;

        return self();
    }
}
