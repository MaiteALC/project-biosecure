package br.com.biosecure.model.product;

import java.util.ArrayList;
import java.time.LocalDate;

public class SampleBag extends SampleContainer {
    private final FilterType filter;
    private final boolean identificationTag;
    private final boolean standUp;
    private final double thicknessMm;
    private final int widthMm;
    private final int heigthMm;

    public SampleBag(String name, double price, String manufacturer, SterilizationMethod sterilizationMethod, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnity measureUnity, int qtdPerPackage, ClosingMethod closingMethod, Material materialType, FilterType filter, boolean hasIdentificationTag, boolean isStandUp, double thicknessMm, double capacity, int widthMm, int heigthMm) {
        super(name, price, manufacturer, sterilizationMethod, batchNumber, expirationDate, packagingType, measureUnity, qtdPerPackage, closingMethod, materialType, capacity);

        validateBioSecuritySafety(materialType);

        this.filter = filter;
        this.identificationTag = hasIdentificationTag;
        this.standUp = isStandUp;
        this.thicknessMm = thicknessMm;
        this.widthMm = widthMm;
        this.heigthMm = heigthMm;
    }

    public enum FilterType {
        NONE,
        FULL_PAGE,
        LATERAL
    }

    private void validateBioSecuritySafety(Material material) {
        if (material == Material.PS || material == Material.BOROSILICATE_GLASS) {
            ArrayList<String> invalids = new ArrayList<>();

            invalids.add("Material");

            throw new BioSecurityException(
                    "Sample bags must be of flexible material (PE, PP). " +
                            getMaterial().getCommercialName() + " is rigid.", invalids
            );
        }
    }

    public FilterType getFilter() {
        return filter;
    }

    public boolean isStandUp() {
        return standUp;
    }

    public boolean hasIdentificationTag() {
        return identificationTag;
    }

    public double getThicknessMm() {
        return thicknessMm;
    }

    public int getHeightMm() {
        return heigthMm;
    }

    public int getWidthMm() {
        return widthMm;
    }
}