package br.com.biosecure.model.product;

import java.time.LocalDate;

public abstract class PersonalProtectiveEquipment extends Product {
    private final Size size;
    private final String certificateOfApproval;
    private final boolean isDisposable;

    public PersonalProtectiveEquipment(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, MeasureUnit.UN, (double) quantityPerPackage);

        validateString(certificateOfApproval, "certificate of approval");

        this.size = size;
        this.certificateOfApproval = certificateOfApproval;
        this.isDisposable = isDisposable;
    }

    public enum Size {
        EXTRA_SMALL("XS"),
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L"),
        EXTRA_LARGE("XL"),
        UNIVERSAL("UN");

        private final String code;

        Size(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public Size getSize() {
        return size;
    }

    public String getCertificateOfApproval() {
        return certificateOfApproval;
    }

    public boolean isDisposable() {
        return isDisposable;
    }
}
