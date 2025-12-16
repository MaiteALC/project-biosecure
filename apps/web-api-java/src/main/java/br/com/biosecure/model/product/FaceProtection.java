package br.com.biosecure.model.product;

import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;

public class FaceProtection extends PPE {
    private final ProtectionType type;
    private final String standardRating;
    private final boolean hasValve;
    private final boolean isAntiFog;

    public FaceProtection(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable, ProtectionType protectionType, String standardRating, boolean isAntiFog, boolean hasValve) {

        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, isDisposable);

        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateString(standardRating, 2, "standard rating", 12, notificationContext);

        if (notificationContext.hasErrors()) {
            throw new InvalidProductAttributeException(notificationContext.getErrors());
        }

        this.type = protectionType;
        this.standardRating = standardRating;
        this.hasValve = type == ProtectionType.MASK_RESPIRATOR ? hasValve : false;
        this.isAntiFog = isAntiFog;
    }

    public enum ProtectionType {
        MASK_RESPIRATOR("MR"),
        SAFETY_GLASSES("SG"), 
        GOGGLES("GG"),       
        FACE_SHIELD("FS");

        private final String code;

        ProtectionType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public ProtectionType getProtectionType() {
        return type;
    }

    public String getStandardRating() {
        return standardRating;
    }

    public boolean hasValve() {
        return hasValve;
    }

    public boolean isAntiFog() {
        return isAntiFog;
    }
}
