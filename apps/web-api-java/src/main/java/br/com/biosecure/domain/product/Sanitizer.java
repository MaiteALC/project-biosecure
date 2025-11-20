package br.com.biosecure.domain.product;

import java.time.LocalDate;

public class Sanitizer extends Product {
    private final String activeIngredient;
    private final PhysicalForm form;
    private final String registerNumber;
    private String useIndications;

    public Sanitizer(String name, double price, String activeIngredient, PhysicalForm form, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnity measureUnity, int qtdPerPackage, String registerNumber, String useIndications) {
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnity, qtdPerPackage);

        this.activeIngredient = activeIngredient;
        this.form = form;
        this.registerNumber = registerNumber;
        this.useIndications = useIndications;
    }

    public enum PhysicalForm {
        LIQUID(1),
        SOLID(2),
        POWDER(3),
        GEL(4),
        SPRAY(5),
        GASEOUS(6);

        private final int code;

        private PhysicalForm(int code) {
            this.code = code;
        }

        public int getPhysicalFormCode() {
            return code;
        }
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public PhysicalForm getPhysicalForm() {
        return form;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getUseIndication() {
        return useIndications;
    }

    public void setUseIndications(String newUseIndications) {
        if (newUseIndications.isBlank()) {
            throw new InvalidProductAttributeException("use indications");
        }

        this.useIndications = newUseIndications;
    }
}
