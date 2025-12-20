package br.com.biosecure.model.product;

import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;

public abstract class PPE extends Product {
    private final Size size;
    private final String certificateOfApproval;
    private final boolean isDisposable;

    public PPE(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, MeasureUnit.U, (double) quantityPerPackage);

        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateString(certificateOfApproval, 5, "certificate of approval", 10,  notificationContext);

        if (notificationContext.hasErrors()) {
            throw new InvalidProductAttributeException(notificationContext.getErrors());
        }

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
        UNIVERSAL("U");

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
