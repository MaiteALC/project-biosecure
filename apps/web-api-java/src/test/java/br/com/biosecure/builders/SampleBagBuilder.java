package br.com.biosecure.builders;

import br.com.biosecure.model.product.SampleBag.*;
import br.com.biosecure.model.product.SampleBag;

public class SampleBagBuilder extends BaseSampleContainerBuilder<SampleBagBuilder, SampleBag> {
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

    @Override
    protected SampleBagBuilder self() {
        return this;
    }

    public static SampleBagBuilder aSampleBag() {
        return new SampleBagBuilder();
    }

    @Override
    public SampleBag build() {
        return new SampleBag(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, sterilizationMethod, closingMethod, material, filter, identificationTag, standUp, thicknessMm, capacityMiliLiters, widthMm, heigthMm);
    }
}
