package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.Product;

public class TestProductBuilder extends ProductBuilder<TestProductBuilder, Product>{

    private static class ProductDummy extends Product {
        public ProductDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage) {    
           
            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);
        }
    }

    @Override
    protected TestProductBuilder self() {
        return this;
    }

    @Override
    public Product build() {
        return new ProductDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);
    }

    public static TestProductBuilder aProduct() {
        return new TestProductBuilder(); 
    }
}
