package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.Product.PackagingType;
import br.com.biosecure.builders.GloveBuilder;
import br.com.biosecure.builders.PetriDishBuilder;
import br.com.biosecure.builders.ProductBuilder;
import br.com.biosecure.builders.SanitizerBuilder;

public class ProductTest {
    
    @Test
    public void shouldCreateProduct_WhenAttributesAreCorrrect() {
        Product aProduct = ProductBuilder.aProduct()
            .withName("Suppose that is a glove box")
            .withPackagingType(PackagingType.BOX)
            .withMeasureUnit(MeasureUnit.PAIR)
            .withQuantityPerPackage(50)
            .build();

        Product anotherProduct = ProductBuilder.aProduct()
            .withMeasureUnit(MeasureUnit.L)
            .withQuantityPerPackage(5.5)
            .withPackagingType(PackagingType.GALLON)
            .build();

        assertNotNull(aProduct);
        assertNotNull(anotherProduct);
    }

    @Test
    public void shouldThrowException_WhenStringAttributeIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withName(null).build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withManufacturer("").build();
        });
        
        InvalidProductAttributeException exception3 = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withBatchNumber("     ").build();
        });

        assertEquals("name", exception.getInvalidAttribute());
        assertEquals("manufacturer", exception2.getInvalidAttribute());
        assertEquals("batch number", exception3.getInvalidAttribute());
    }

    @Test
    public void shouldThrowException_WhenProductAttributeIsInvalid()  {
        InvalidProductAttributeException priceExpception = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withPrice(0).build();
        });
        
        InvalidProductAttributeException priceExpception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withPrice(1000000).build();
        });

        assertEquals("price", priceExpception.getInvalidAttribute());
        assertEquals("price", priceExpception2.getInvalidAttribute());
        
        InvalidProductAttributeException quantityExpception = assertThrows(InvalidProductAttributeException.class, () ->{
            ProductBuilder.aProduct().withQuantityPerPackage(0).build();
        });
        
        InvalidProductAttributeException quantityExpception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct().withQuantityPerPackage(100000).build();
        });

        assertEquals("quantity per package", quantityExpception.getInvalidAttribute());
        assertEquals("quantity per package", quantityExpception2.getInvalidAttribute());

         InvalidProductAttributeException measureUnitException = assertThrows(InvalidProductAttributeException.class, () -> {
            ProductBuilder.aProduct()
                .withPackagingType(PackagingType.INDIVIDUAL)
                .withMeasureUnit(MeasureUnit.MG) // <-- the incoherence between measure unit and packaging type
                .build(); 
        });

        assertEquals("measure unit", measureUnitException.getInvalidAttribute());
    }

    @Test
    public void shouldSetQuantityPerPackageToOne_WhenPackagingTypeIsIndividual() {
        Product aProduct =  ProductBuilder.aProduct()
            .withPackagingType(PackagingType.INDIVIDUAL)
            .withMeasureUnit(MeasureUnit.UN)
            .build();

        assertEquals(1, aProduct.getQuantityPerPackage());
    }

    @Test
    public void shouldThrowException_WhenExpirationDateIsInvalid() {
        InvalidProductAttributeException dateException = assertThrows(InvalidProductAttributeException.class, () ->{
            ProductBuilder.aProduct()
                .withExpirationDate(LocalDate.now().minusDays(1))
                .build();
        });

        assertEquals("expiration date", dateException.getInvalidAttribute());

        InvalidProductAttributeException dateException2 = assertThrows(InvalidProductAttributeException.class, () ->{
            ProductBuilder.aProduct()
                .withExpirationDate(LocalDate.now())
                .build();
        });

        assertEquals("expiration date", dateException2.getInvalidAttribute());

        InvalidProductAttributeException dateException3 = assertThrows(InvalidProductAttributeException.class, () ->{
            ProductBuilder.aProduct()
                .withExpirationDate(LocalDate.of(2048, 2, 11))
                .build();
        });

        assertEquals("expiration date", dateException3.getInvalidAttribute());
    }

    @Test
    public void shouldVerifyCorrectly_WhenTwoProductsAreDifferentOrEquals() {
        Product productA = PetriDishBuilder.aPetriDish()
            .withName("Product A")
            .withManufacturer("Manufacturer A")
            .withPackagingType(PackagingType.BOX)
            .withQuantityPerPackage(200)
            .withBatchNumber("111")
            .build();

        Product productA2 = PetriDishBuilder.aPetriDish()
            .withName("Product A")
            .withManufacturer("Manufacturer A")
            .withPackagingType(PackagingType.BOX)
            .withQuantityPerPackage(200)
            .withBatchNumber("111")
            .build();

        assertEquals(productA, productA2);
        assertEquals(productA.getSku(), productA2.getSku());

        Product productB = SanitizerBuilder.aSanitizer()
            .withName("Product B")
            .withBatchNumber("222")
            .withPackagingType(PackagingType.GALLON)
            .withQuantityPerPackage(5)
            .withManufacturer("Manufacturer B")
            .build();
        
        Product productC = GloveBuilder.aGlove()
        .withName("Product C")
        .withBatchNumber("333")
        .withPackagingType(PackagingType.INDIVIDUAL)
        .withManufacturer("Manufacturer C")
        .build();

        assertNotEquals(productB, productC);
        assertNotEquals(productB.getSku(), productC.getSku());
    }
}
