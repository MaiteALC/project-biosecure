package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.TestTube;
import br.com.biosecure.model.product.Product.PackagingType;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.model.product.TestTube.*;

public class TestTubeBuilder {
    // General attributes of products
    private String name = "Sanitizer Test";
    private double price = 54.90;
    private String manufacturer = "Test Manufacturer";
    private String batchNumber = "Batch-1A";
    private LocalDate expirationDate = LocalDate.of(2027, 6, 12);
    private PackagingType packagingType = PackagingType.BOX;
    private int quantityPerPackage = 15;

    // Attributes of super class Sample Container
    private ClosingMethod closingMethod = ClosingMethod.CELLULOSE_STOPPER;
    private SterilizationMethod sterilizationMethod = SterilizationMethod.GAMMA_RAYS;
    private Material material = Material.PS;

    // Specific attributes of Test Tubes
    private int maxRCF;
    private BottomType bottomType;
    private boolean graduated;
    private CapColor capColor;
    private double diameterMm;
    private double heightMm;

    public TestTubeBuilder withClosingMethod(ClosingMethod closingMethod) {
        this.closingMethod = closingMethod;

        return this;
    }

    public TestTubeBuilder withSterilizationMethod(SterilizationMethod sterilizationMethod) {
        this.sterilizationMethod = sterilizationMethod;

        return this;
    }

    public TestTubeBuilder withMaterial(Material material) {
        this.material = material;

        return this;
    }

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

    public TestTubeBuilder withHeightMm(double heightMm) {
        this.heightMm = heightMm;

        return this;
    }

    public static TestTubeBuilder aTestTube() {
        return new TestTubeBuilder();
    }

    public TestTube build() {
        return new TestTube(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, material, maxRCF, bottomType, graduated, capColor, diameterMm, heightMm);
    }
}
