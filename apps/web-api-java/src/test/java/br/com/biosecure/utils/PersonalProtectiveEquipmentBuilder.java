package br.com.biosecure.utils;

import java.time.LocalDate;
import br.com.biosecure.model.product.PersonalProtectiveEquipment;
import br.com.biosecure.model.product.PersonalProtectiveEquipment.Size;
 
public class PersonalProtectiveEquipmentBuilder extends ProductBuilder<PersonalProtectiveEquipmentBuilder, PersonalProtectiveEquipment> {
    // Specific attributes of Personal Protective Equipment (PPE)
    protected Size size = Size.UNIVERSAL;
    protected String certificateOfApproval = "CA-54967";
    protected boolean isDisposable = false;

    private static class PersonalProtectiveEquipmentDummy extends PersonalProtectiveEquipment {
        public PersonalProtectiveEquipmentDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable) {

            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, isDisposable);
        }
    }

    public PersonalProtectiveEquipmentBuilder withSize(Size size) {
        this.size = size;

        return this;
    }

    public PersonalProtectiveEquipmentBuilder withCertificateOfApproval(String certificateOfApproval) {
        this.certificateOfApproval = certificateOfApproval;

        return this;
    }

    public PersonalProtectiveEquipmentBuilder withDisposable(boolean isDisposable) {
        this.isDisposable = isDisposable;

        return this;
    }

    public static PersonalProtectiveEquipmentBuilder aPPE() {
        return new PersonalProtectiveEquipmentBuilder();
    }
    
    @Override
    protected PersonalProtectiveEquipmentBuilder self() {
        return this;
    }

    @Override
    public PersonalProtectiveEquipment build() {
        return new PersonalProtectiveEquipmentDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, size, certificateOfApproval, isDisposable);
    }
}
