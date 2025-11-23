package br.com.biosecure.model.product;

import java.util.ArrayList;
import java.time.LocalDate;

public class TestTube extends SampleContainer {
    private final int maxRCF;
    private final BottomType bottomType;
    private final boolean graduated;
    private final CapColor capColor;
    private final double diameterMm;
    private final double heightMm;

    public TestTube(String name, double price, String manufacturer, SterilizationMethod sterilizationMethod, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int qtdPerPackage, ClosingMethod closingMethod, Material materialType, int maxRCF, BottomType bottomType, boolean isGraduated, CapColor capColor, double diameterMm, double heightMm) {
        super(name, price, manufacturer, sterilizationMethod, batchNumber, expirationDate, packagingType, MeasureUnity.UN, qtdPerPackage, closingMethod, materialType, calculateNominalCapacity(diameterMm, heightMm));

        validateBioSecuritySafety(materialType, bottomType, maxRCF);

        this.maxRCF = maxRCF;
        this.bottomType = bottomType;
        this.graduated = isGraduated;
        this.capColor = capColor;
        this.diameterMm = diameterMm;
        this.heightMm = heightMm;
    }

    public enum BottomType {
        CONICAL,
        FLAT,
        ROUND,
        SKIRTED
    }

    public enum CapColor {
        BLUE("Sodium citrate"),
        GRAY("Potassium fluoride and EDTA"),
        GREEN("Heparin"),
        PURPLE("EDTA"),
        RED("Clot activator"),
        WHITE("None"),
        YELLOW("Clot activator and separator gel");

        private final String chemicalAdditive;

        CapColor(String chemicalAdditive) {
            this.chemicalAdditive = chemicalAdditive;
        }

        public String getChemicalAdditive() {
            return chemicalAdditive;
        }
    }

    private void validateBioSecuritySafety(Material material, BottomType bottomType, int maxRCF) {
        ArrayList<String> invalids = new ArrayList<>();

        invalids.add("maximum RCF");

        if (material == Material.BOROSILICATE_GLASS && maxRCF > 5000) {
            invalids.add("Material");

            throw new BioSecurityException(
                    "Glass tubes rarely supports RCF greater than 5000g. Verify the specifications.", invalids
            );
        }

        if (bottomType == BottomType.FLAT && maxRCF > 10000) {
            invalids.add("Bottom type");

            throw new BioSecurityException(
                    "Flat bottoms concentrate the tension. RCF greater than 10.000g is uncommon for flat bottoms. Verify the specifications", invalids
            );
        }
    }

    public int getMaxRCF() {
        return maxRCF;
    }

    public BottomType getBottomType() {
        return bottomType;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public double getHeightMm() {
        return heightMm;
    }

    public double getDiameterMm() {
        return diameterMm;
    }

    public CapColor getCapColor() {
        return capColor;
    }

    public static double calculateNominalCapacity(double diameter, double height) {
        // V = pi * r^2 * h
        double radius = diameter / 2.0;

        double volumeCubicMm = Math.PI * Math.pow(radius, 2) * height;

        return Math.round(volumeCubicMm / 1000.0); // Conversion of mm^3 to mL
    }
}
