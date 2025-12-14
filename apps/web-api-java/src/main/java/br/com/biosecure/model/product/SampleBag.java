package br.com.biosecure.model.product;

import java.util.ArrayList;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import java.time.LocalDate;

public class SampleBag extends SampleContainer {
    private final FilterType filter;
    private final boolean identificationTag;
    private final boolean standUp;
    private final double thicknessMm;
    private final double widthMm;
    private final double heigthMm;
    private final double capacityMiliLiters;

    public SampleBag(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, SterilizationMethod sterilizationMethod, ClosingMethod closingMethod, Material materialType, FilterType filter, boolean hasIdentificationTag, boolean isStandUp, double thicknessMm, double capacityMiliLiters, double widthMm, double heigthMm) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, sterilizationMethod, closingMethod, materialType);

        NotificationContext notification = new NotificationContext();
        
        NumberUtils.validateNumericalAttribute(heigthMm, 1, "heigth (mm)", 999, notification);
        NumberUtils.validateNumericalAttribute(widthMm, 1, "width (mm)", 999, notification);
        
        NumberUtils.validateNumericalAttribute(thicknessMm, 1, "thickness (mm)", 99, notification);

        NumberUtils.validateNumericalAttribute(capacityMiliLiters, 1, "capacity (mL)", 9999, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        validateBioSafetyRules(materialType);

        this.filter = filter;
        this.identificationTag = hasIdentificationTag;
        this.standUp = isStandUp;
        this.thicknessMm = thicknessMm;
        this.widthMm = widthMm;
        this.heigthMm = heigthMm;
        this.capacityMiliLiters = capacityMiliLiters;
    }
    
    private void validateBioSafetyRules(Material material) {
        if (material != Material.PE && material != Material.PP) {
            ArrayList<String> invalids = new ArrayList<>();
            
            invalids.add("Material");
            
            throw new BioSecurityException(
                "Sample bags must be of flexible material (PE, PP). " + getMaterial().getCommercialName() + " is rigid.", invalids
            );
        }
    }
    
    public enum FilterType {
        NONE,
        FULL_PAGE,
        LATERAL
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

    public double getHeightMm() {
        return heigthMm;
    }

    public double getWidthMm() {
        return widthMm;
    }

    public double getCapacityMiliLiters() {
        return capacityMiliLiters;
    }
}