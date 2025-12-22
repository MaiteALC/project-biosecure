package br.com.biosecure.model.product;

public class SKU {
    private final String code;

    public SKU(Product product) {
        this.code = generateFor(product);
    }

    private String generateFor(Product product) {
        if (product == null) {
            throw new NullPointerException("The product mustn't be null.");
        }

        StringBuilder code = new StringBuilder();
        code.append('-');

        String packagingTypeCode = product.getPackagingType().getCode();
        code.append(packagingTypeCode);

        if (packagingTypeCode != "INDV") {
            code.append(formatDoubleAsStr(product.getQuantityPerPackage()));
        }

        code.append('-');

        switch (product) {
            case Sanitizer sanitizer -> incrementForSanitizer(code, sanitizer);

            case CultureMedia cultureMedia -> incrementForCultureMedia(code, cultureMedia);
            
            case SampleContainer sampleContainer -> incrementForSampleContainer(code, sampleContainer);

            case PPE ppe -> incrementForPpe(code, ppe);

            case null, default -> throw new SkuGenerationException("Unknow product type. Generation of SKU code is unavailable for this subclass.", product);
        }

        return code.toString();
    }

    private StringBuilder incrementForPpe(StringBuilder currentCode, PPE aPpe) {
        switch (aPpe) {
            case Glove glove -> {
            currentCode.insert(0, "GLV");
            currentCode.append(formatDoubleAsStr(glove.getThicknessMils()));                
            currentCode.append(glove.getMaterial().getCode()); // Always returns 2 letters
            currentCode.append('-');
            currentCode.append(glove.hasLongBarrel() ? "L" : "S");
            }

            case FaceProtection faceProtection -> {
                currentCode.insert(0, "FPT");
                currentCode.append(faceProtection.getProtectionType().getCode()); // Always returns 2 letters
                currentCode.append('-');
                currentCode.append(faceProtection.isAntiFog() ? "AF" : "C");
            }

            case LabCoat labCoat -> {
                currentCode.insert(0, "COAT");
                currentCode.append(labCoat.getFabricType().getCode());
                currentCode.append(labCoat.getGrammage());
                currentCode.append('-');
            }
            
            case null, default -> throw new SkuGenerationException("Product with unknow type of 'Personal Protective Equipment' (PPE). Generation of SKU code is unavailable for this subclass.", aPpe);
        }

        currentCode.append(aPpe.getSize().getCode());

        return currentCode;
    }

    private StringBuilder incrementForSanitizer(StringBuilder currentCode, Sanitizer aSanitizer) {
        currentCode.insert(0, "SAN");

        currentCode.insert(currentCode.lastIndexOf("-"), aSanitizer.getMeasureUnit());

        currentCode.append(aSanitizer.getActiveIngredient().getCode());
        currentCode.append('-');

        currentCode.append(aSanitizer.getPhysicalForm().getCode());
        currentCode.append(aSanitizer.isFlammable() ? "FL" : "NF");

        return currentCode;
    }

    private StringBuilder incrementForSampleContainer(StringBuilder currentCode, SampleContainer aContainer) {
        switch (aContainer) {
            case SampleBag sampleBag -> {
                currentCode.insert(0, "BAG");

                currentCode.append(sampleBag.getFilter().getCode());
                currentCode.append(formatDoubleAsStr(sampleBag.getCapacityMiliLiters()));
                currentCode.append("-");

                currentCode.append(sampleBag.hasIdentificationTag() ? "ID" : "AN");
                currentCode.append(sampleBag.isStandUp() ? "U" : "D");
            }

            case TestTube testTube -> {
                currentCode.insert(0, "TUB"); 

                currentCode.append(testTube.getMaterial().getCode());

                currentCode.append(formatDoubleAsStr(testTube.getHeightMm()));
                currentCode.append('x');
                currentCode.append(formatDoubleAsStr(testTube.getDiameterMm()));

                currentCode.append('-');

                currentCode.append(testTube.isSterile() ? "S" : "N");
                currentCode.append(testTube.getBottomType().toString().charAt(0));
            }

            case PetriDish petriDish -> {
                currentCode.insert(0, "PTD");

                currentCode.append(formatDoubleAsStr(petriDish.getDiameter()));
                currentCode.append('x');
                currentCode.append(formatDoubleAsStr(petriDish.getHeight()));

                currentCode.append('-');

                currentCode.append(petriDish.getMaterial().getCode());
            }

            case null, default -> throw new SkuGenerationException("Product with unknow type of 'Sample Container'. Generation of SKU code is unavailable for this subclass.", aContainer);
        }

        return currentCode;
    }

    private StringBuilder incrementForCultureMedia(StringBuilder currentCode, CultureMedia aCultureMedia) {
        currentCode.insert(0, "CTM");

        currentCode.append(formatDoubleAsStr(aCultureMedia.getQuantityPerUnit()));
        currentCode.append(aCultureMedia.getQuantityUnit());
        
        currentCode.append('-');
        
        currentCode.append(aCultureMedia.isProtectOfLight() ? "F" : "N");
        currentCode.append(aCultureMedia.getStorageConditions().getCode());

        return currentCode;
    }

    private String formatDoubleAsStr(double num) {
        String numAsStr = String.valueOf(num);
        
        if ((int) num == num) {
            if (num >= 1000) {
                numAsStr = (num / 1000) + "k";
            }

            if (numAsStr.endsWith(".0") || numAsStr.endsWith(".0k")) {
                return numAsStr.replace(".0", "");
            }
        }

        return numAsStr;
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