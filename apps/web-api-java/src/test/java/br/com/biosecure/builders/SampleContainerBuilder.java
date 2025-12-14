package br.com.biosecure.builders;

import java.time.LocalDate;
import br.com.biosecure.model.product.SampleContainer;

public class SampleContainerBuilder extends BaseSampleContainerBuilder<SampleContainerBuilder, SampleContainer> {
    
    private static class SampleContainerDummy extends SampleContainer {
        public SampleContainerDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, SterilizationMethod sterilizationMethod, ClosingMethod closingMethod, Material materialType) {
        
            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, materialType);
        }
    }

    @Override
    protected SampleContainerBuilder self() {
        return this;
    }

    public static SampleContainerBuilder aSampleContainer() {
        return new SampleContainerBuilder();
    }

    @Override
    public SampleContainer build() {
        return new SampleContainerDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, sterilizationMethod, closingMethod, material);
    }
}
