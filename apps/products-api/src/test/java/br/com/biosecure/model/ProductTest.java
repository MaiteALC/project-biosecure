package br.com.biosecure.model;

import br.com.biosecure.builders.DummyProduct;
import br.com.biosecure.builders.GloveTestBuilder;
import br.com.biosecure.builders.PetriDishTestBuilder;
import br.com.biosecure.builders.SanitizerTestBuilder;
import br.com.biosecure.model.Product.MeasureUnit;
import br.com.biosecure.model.Product.PackagingType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    
    @Test
    void shouldCreateProduct_WhenAttributesAreCorrect() {
        Product aProduct = DummyProduct.builder()
            .name("Suppose that is a glove box")
            .packagingType(PackagingType.BOX)
            .measureUnit(MeasureUnit.U)
            .quantityPerPackage(50)
            .build();

        Product anotherProduct = DummyProduct.builder()
            .measureUnit(MeasureUnit.L)
            .quantityPerPackage(5.5)
            .packagingType(PackagingType.GALLON)
            .build();

        assertNotNull(aProduct);
        assertNotNull(anotherProduct);
    }

    @Test
    void shouldThrowException_WhenStringAttributeIsInvalid() {
        String expectedMessage = "These attributes are invalids:\n\t - name | The string is null.\n\t - manufacturer | The string is empty.\n\t - batch number | The string is empty.\n";

        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder()
            .name(null)
            .manufacturer("")
            .batchNumber("     ")
            .build()
        );

        ArrayList<String> invalids = new ArrayList<>();
        invalids.add("name");
        invalids.add("manufacturer");
        invalids.add("batch number");

        assertEquals("[name, manufacturer, batch number]", exception.getInvalidAttribute());
        assertIterableEquals(invalids, exception.getInvalidAttributesArray());

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenProductAttributeIsInvalid()  {
        InvalidProductAttributeException priceException = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder().price(0).build());
        
        InvalidProductAttributeException priceException2 = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder().price(1000000).build());

        assertEquals("price", priceException.getInvalidAttribute());
        assertEquals("price", priceException2.getInvalidAttribute());
        
        InvalidProductAttributeException quantityException = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder().quantityPerPackage(0).build());
        
        InvalidProductAttributeException quantityException2 = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder().quantityPerPackage(100000).build());

        assertEquals("quantity per package", quantityException.getInvalidAttribute());
        assertEquals("quantity per package", quantityException2.getInvalidAttribute());
    }

    @Test
    void shouldEnsureCoherence_BetweenPackagingTypeAndMeasureUnit() {
        Product aProduct =  DummyProduct.builder()
            .packagingType(PackagingType.INDIVIDUAL)
            .quantityPerPackage(50)
            .build(); // incoherence between these attributes will be corrected based on packaging type

        assertEquals(1, aProduct.getQuantityPerPackage());

        Product dummy = DummyProduct.builder()
                .packagingType(PackagingType.INDIVIDUAL)
                .measureUnit(MeasureUnit.MG)
                .build(); // incoherence between these attributes will be corrected based on packaging type

        assertEquals(MeasureUnit.U, dummy.getMeasureUnit());
    }

    @Test
    void shouldThrowException_WhenExpirationDateIsInvalid() {
        InvalidProductAttributeException dateException = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder()
            .expirationDate(LocalDate.now().minusDays(1))
            .build());

        assertEquals("expiration date", dateException.getInvalidAttribute());

        InvalidProductAttributeException dateException2 = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder()
            .expirationDate(LocalDate.now())
            .build());

        assertEquals("expiration date", dateException2.getInvalidAttribute());

        InvalidProductAttributeException dateException3 = assertThrows(InvalidProductAttributeException.class, () -> DummyProduct.builder()
            .expirationDate(LocalDate.of(2048, 2, 11))
            .build());

        assertEquals("expiration date", dateException3.getInvalidAttribute());
    }

    @Test
    void shouldVerifyCorrectly_WhenTwoProductsAreDifferentOrEquals() {
        Product productA = PetriDishTestBuilder.aPetriDish()
            .withName("Product A")
            .withManufacturer("Manufacturer A")
            .withPackagingType(PackagingType.BOX)
            .withQuantityPerPackage(200)
            .withBatchNumber("111")
            .build();

        Product productA2 = PetriDishTestBuilder.aPetriDish()
            .withName("Product A")
            .withManufacturer("Manufacturer A")
            .withPackagingType(PackagingType.BOX)
            .withQuantityPerPackage(200)
            .withBatchNumber("111")
            .build();

        assertEquals(productA, productA2);
        assertEquals(productA.getSku(), productA2.getSku());

        Product productB = SanitizerTestBuilder.aSanitizer()
            .withName("Product B")
            .withBatchNumber("222")
            .withPackagingType(PackagingType.GALLON)
            .withQuantityPerPackage(5)
            .withManufacturer("Manufacturer B")
            .build();
        
        Product productC = GloveTestBuilder.aGlove()
        .withName("Product C")
        .withBatchNumber("333")
        .withPackagingType(PackagingType.INDIVIDUAL)
        .withManufacturer("Manufacturer C")
        .build();

        assertNotEquals(productB, productC);
        assertNotEquals(productB.getSku(), productC.getSku());
    }
}
