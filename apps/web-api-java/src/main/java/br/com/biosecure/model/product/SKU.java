package br.com.biosecure.model.product;

public class SKU {
    private final String code;

    public SKU(Product product) {
        this.code = generateFor(product);
    }

    private String generateFor(Product product) {
        String name = product.getName();
        String code = name.length() >= 3 ? name.substring(0, 4) : name;

        code += "-" + product.getPackagingType().getCode(); // Always returns 1 or 2 letters

        double quantity = product.getQuantityPerPackage(); 
        code += quantity >= 1000 ? (quantity / 1000) + "k" : quantity;

        code += product.getMeasureUnit() + "-";

        if (product instanceof Sanitizer sanitizer) {
            code += sanitizer.getPhysicalForm().getCode(); // Always returns 2 letters

            code += sanitizer.isFlammable() ? "FL" : "NF";
        }

        else if (product instanceof CultureMedia cultureMedia) {
            code += cultureMedia.isProtectOfLight() ? "F" : "N";

            code += cultureMedia.getStorageConditions().getCode(); // Always returns 3 letters
        }

        else if (product instanceof SampleContainer sampleContainer) {
            if (sampleContainer instanceof SampleBag sampleBag) {
                code += sampleBag.hasIdentificationTag() ? "I" : "A";

                code += sampleBag.isStandUp() ? "U" : "D";
            }

            else if (sampleContainer instanceof TestTube testTube) {
                code += testTube.isGraduated() ? "G" : "L";

                code += testTube.getBottomType().toString().charAt(0);
            }

            else if (sampleContainer instanceof PetriDish petriDishes) {
                code += petriDishes.getDiameter() + "X";

                code += petriDishes.getHeight();
            }

            else {
                throw new SkuGenerationException(
                    "Product with unknow type of 'Sample Container'. Generation of SKU code is unavailable for this subclass.", sampleContainer
                );
            }
        }

        else if (product instanceof PersonalProtectiveEquipment ppe) {
            code += ppe.getSize().getCode(); // Always returns 1 or 2 letters
            
            if (ppe instanceof Glove glove) {
                code += glove.hasLongBarrel() ? "L" : "S";

                code += glove.getThicknessMils();
            }

            else if (ppe instanceof FaceProtection faceProtection) {
                code += faceProtection.getProtectionType().getCode(); // Always returns 2 letters
            }

            else if (ppe instanceof LabCoat labCoat) {
                code += labCoat.getFabricType().toString().charAt(0);

                code += labCoat.getGrammage();
            }

            else {
                throw new SkuGenerationException(
                    "Product with unknow type of 'Personal Protective Equipment' (PPE). Generation of SKU code is unavailable for this subclass.", ppe
                );
            }
        }

        else {
            throw new SkuGenerationException(
                "Unknow product type. Generation of SKU code is unavailable for this subclass.", product
            );
        }

        return code.toUpperCase();
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