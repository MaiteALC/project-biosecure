package br.com.biosecure.model.product;

import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;

public class LabCoat extends PPE {
    private final FabricType fabricType;
    private final int grammage;
    private final CuffStyle cuffStyle;
    private final CollarType collarType;

    public LabCoat(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, FabricType fabricType, int grammage, CuffStyle cuffStyle, CollarType collarType) {
       
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, fabricType.isDisposableFabric());

        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(grammage, 20, "grammage (g/cm²)", 350, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        this.fabricType = fabricType;
        this.grammage = grammage;
        this.cuffStyle = cuffStyle;
        this.collarType = collarType;
    }

    public enum FabricType {
        COTTON_100("Cotton", false, "CT"),
        POLYESTER("Polyester", false, "PO"),
        POLYPROPYLEN("SPP", true, "PP"),
        MIX_COTTON_POLYESTER("Cotton and polyester", false, "CP");

        private String commercialName;
        private boolean disposable;
        private String code;

        FabricType(String commercialName, boolean disposable, String code) {
            this.commercialName = commercialName;
            this.disposable = disposable;
            this.code = code;
        }

        public String getCommercialName() {
            return commercialName;
        }

        public boolean isDisposableFabric() {
            return disposable;
        }

        public String getCode() {
            return code;
        }
    }

    public enum CuffStyle {
        OPEN_CUFF,
        KNITTED_CUFF,
        ELASTIC_CUFF
    }

    public enum CollarType {
        V_NECK,
        HIGH_NECK
    }

    public FabricType getFabricType() {
        return fabricType;
    }

    public int getGrammage() {
        return grammage;
    }

    public CuffStyle getCuffStyle() {
        return cuffStyle;
    }

    public CollarType getCollarType() {
        return collarType;
    }
}
