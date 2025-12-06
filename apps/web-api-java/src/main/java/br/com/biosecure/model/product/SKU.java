package br.com.biosecure.model.product;

public class SKU {
    private final String code;

    public SKU(Product product) {
        this.code = generateFor(product);
    }

    private String generateFor(Product product) {
        String name = product.getName();
        String value = name.length() >= 3 ? name.substring(0, 4) : name;

        value += "-" + product.getPackagingType().getCode(); // Always returns 1 or 2 letters

        double quantity = product.getQuantityPerPackage(); 
        value += quantity >= 1000 ? (quantity / 1000) + "k" : quantity;

        value += product.getMeasureUnit() + "-";

        if (product instanceof Sanitizer sanitizer) {
            value += sanitizer.getPhysicalForm().getCode(); // Always returns 2 letters

            value += sanitizer.isFlammable() ? "FL" : "NF";
        }

        if (product instanceof CultureMedia cultureMedia) {
            value += cultureMedia.isProtectOfLight() ? "F" : "N";

            value += cultureMedia.getStorageConditions().getCode(); // Always returns 3 letters
        }

        if (product instanceof SampleContainer sampleContainer) {
            if (sampleContainer instanceof SampleBag sampleBag) {
                value += sampleBag.hasIdentificationTag() ? "I" : "A";

                value += sampleBag.isStandUp() ? "U" : "D";
            }

            if (sampleContainer instanceof TestTube testTube) {
                value += testTube.isGraduated() ? "G" : "L";

                value += testTube.getBottomType().toString().charAt(0);
            }

            if (sampleContainer instanceof PetriDish petriDishes) {
                value += petriDishes.getDiameter() + "X";

                value += petriDishes.getHeight();
            }
        }

        if (product instanceof PersonalProtectiveEquipment ppe) {
            value += ppe.getSize().getCode(); // Always returns 1 or 2 letters
            
            if (ppe instanceof Glove glove) {
                value += glove.hasLongBarrel() ? "L" : "S";

                value += glove.getThicknessMils();
            }

            if (ppe instanceof FaceProtection faceProtection) {
                value += faceProtection.getProtectionType().getCode(); // Always returns 2 letters
            }

            if (ppe instanceof LabCoat labCoat) {
                value += labCoat.getFabricType().toString().charAt(0);

                value += labCoat.getGrammage();
            }
        }

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