package br.com.biosecure.builders;

import br.com.biosecure.model.product.TestTube;
import br.com.biosecure.model.product.TestTube.*;

public class TestTubeBuilder extends BaseSampleContainerBuilder<TestTubeBuilder, TestTube> {
    // Specific attributes of Test Tubes
    private int maxRCF = 7000;
    private BottomType bottomType = BottomType.ROUND;
    private boolean graduated = true;
    private CapColor capColor = CapColor.WHITE;
    private double diameterMm = 12;
    private double heightMm = 75;

    public TestTubeBuilder withMaxRCF(int maxRCF) {
        this.maxRCF = maxRCF;

        return this;
    }

    public TestTubeBuilder withBottomType(BottomType bottomType) {
        this.bottomType = bottomType;

        return this;
    }

    public TestTubeBuilder withGraduated(boolean graduated) {
        this.graduated = graduated;

        return this;
    }

    public TestTubeBuilder withCapColor(CapColor capColor) {
        this.capColor = capColor;

        return this;
    }

    public TestTubeBuilder withDiameterMm(double diameterMm) {
        this.diameterMm = diameterMm;

        return this;
    }
    
    public TestTubeBuilder withDiameterMm(int diameterMm) {
        this.diameterMm = diameterMm;

        return this;
    }

    public TestTubeBuilder withHeightMm(double heightMm) {
        this.heightMm = heightMm;

        return this;
    }
    
    public TestTubeBuilder withHeightMm(int heightMm) {
        this.heightMm = heightMm;

        return this;
    }

    @Override
    protected TestTubeBuilder self() {
        return this;
    }

    public static TestTubeBuilder aTestTube() {
        return new TestTubeBuilder();
    }

    @Override
    public TestTube build() {
        return new TestTube(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, sterilizationMethod, closingMethod, material, maxRCF, bottomType, graduated, capColor, diameterMm, heightMm);
    }
}
