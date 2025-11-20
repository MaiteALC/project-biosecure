package br.com.biosecure.domain.product;

public class SKU {
    private final String code;

    public SKU(Product product) {
        this.code = generateFor(product);
    }

    private String generateFor(Product product) {
        String value = product.getName().substring(0, 4) + "-";

        if (product instanceof Sanitizer sanitizer) {
            value += sanitizer.getActiveIngredient().substring(0, 3);

            value += String.valueOf(sanitizer.getQtdPerPackage());

            value += String.valueOf(sanitizer.getMeasureUnity());
        }

        if (product instanceof CultureMedia cultureMedia) {
            value += cultureMedia.isReadyToUse() ? "1" : "0";

            value += String.valueOf(cultureMedia.getPhysicalForm().getPhysicalFormCode());

            value += String.valueOf(cultureMedia.getStorageConditions());
        }

        // TODO finish the implementation of this method (based on another attributes of product subclasses)

        return value.toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SKU otherSku = (SKU) obj;

        return code.equals(otherSku.code);
    }

    public String getSkuCode() {
        return code;
    }
}