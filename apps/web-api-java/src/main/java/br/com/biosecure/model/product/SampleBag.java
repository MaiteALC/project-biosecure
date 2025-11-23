package br.com.biosecure.model.product;

import java.time.LocalDate;

public class SampleBag extends SampleContainer {
    private final boolean filter;
    private final boolean identificationTag;
    private final boolean standUp;
    private final double thickness;
    private final ThicknessUnity thicknessUnity;

    public SampleBag(String name, double price, String manufacturer, SterilizationMethod sterilizationMethod, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnity measureUnity, int qtdPerPackage, ClosingMethod closingMethod, Material materialType, boolean hasFilter, boolean hasIdentificationTag, boolean isStandUp, double thickness, ThicknessUnity thicknessUnity, double capacity) {
        super(name, price, manufacturer, sterilizationMethod, batchNumber, expirationDate, packagingType, measureUnity, qtdPerPackage, closingMethod, materialType, capacity);

        this.filter = hasFilter;
        this.identificationTag = hasIdentificationTag;
        this.standUp = isStandUp;
        this.thickness = thickness;
        this.thicknessUnity = thicknessUnity;
    }

    public enum ThicknessUnity {
        MM,
        GAUGE,
        MIL
    }

    public ThicknessUnity getThicknessUnity() {
        return thicknessUnity;
    }

    public boolean hasFilter() {
        return filter;
    }

    public boolean isStandUp() {
        return standUp;
    }

    public boolean hasIdentificationTag() {
        return identificationTag;
    }

    public double getThickness() {
        return thickness;
    }
}
