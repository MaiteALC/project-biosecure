package br.com.biosecure.builders;

import java.time.LocalDate;
import br.com.biosecure.model.product.PPE;

public class PpeBuider extends BasePpeBuilder<PpeBuider, PPE> {
     private static class PersonalProtectiveEquipmentDummy extends PPE {
        public PersonalProtectiveEquipmentDummy(String name, double price, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, int quantityPerPackage, Size size, String certificateOfApproval, boolean isDisposable) {

            super(name, price, manufacturer, batchNumber, expirationDate, packagingType, quantityPerPackage, size, certificateOfApproval, isDisposable);
        }
    }

    @Override
    protected PpeBuider self() {
        return this;
    }

    public static PpeBuider aPPE() {
        return new PpeBuider();
    }

    @Override
    public PPE build() {
        return new PersonalProtectiveEquipmentDummy(name, price, manufacturer, batchNumber, expirationDate, packagingType, (int) quantityPerPackage, size, certificateOfApproval, isDisposable);
    }
}
