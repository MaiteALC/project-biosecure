package br.com.biosecure.model.product;

import java.time.LocalDate;

public class LabCoat extends PersonalProtectiveEquipment {
    private final FabricType fabricType;
    private final int grammage;
    private final CuffStyle cuffStyle;
    private final CollarType collarType;

    public LabCoat(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, FabricType fabricType, int grammage, CuffStyle cuffStyle, CollarType collarType) {
       
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, fabricType == FabricType.SMS_NON_WOVEN ? true : false /* <-- is disposable?*/);

        this.fabricType = fabricType;
        this.grammage = grammage;
        this.cuffStyle = cuffStyle;
        this.collarType = collarType;
    }

    public enum FabricType {
        COTTON_100,
        POLYESTER,
        SMS_NON_WOVEN
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
