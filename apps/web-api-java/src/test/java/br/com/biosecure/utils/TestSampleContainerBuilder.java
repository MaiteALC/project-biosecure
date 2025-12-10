package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.SampleContainer;

public class TestSampleContainerBuilder extends SampleContainerBuilder<TestSampleContainerBuilder, SampleContainer> {
    
    private static class SampleContainerDummy extends SampleContainer {
        public SampleContainerDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, SterilizationMethod sterilizationMethod, ClosingMethod closingMethod, Material materialType, double capacityML) {
        
            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, materialType, capacityML);
        }
    }

    @Override
    protected TestSampleContainerBuilder self() {
        return this;
    }

    public static TestSampleContainerBuilder aSampleContainer() {
        return new TestSampleContainerBuilder();
    }

    @Override
    public SampleContainer build() {
        return new SampleContainerDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, sterilizationMethod, closingMethod, material, capacityMiliLiters);
    }
}
