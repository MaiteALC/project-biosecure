package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.utils.TestSampleContainerBuilder;

public class SampleContainerTest {
    
    @Test
    public void shouldCreateValidSampleContainer() {
        SampleContainer aSampleContainer = TestSampleContainerBuilder.aSampleContainer()
            .withCapacityMiliLiters(20)
            .withSterilizationMethod(SterilizationMethod.E_BEAM)
            .withClosingMethod(ClosingMethod.HEAT_SEALABLE)
            .withMaterial(Material.PP)
            .build();

        assertNotNull(aSampleContainer);
        assertEquals(MeasureUnit.UN, aSampleContainer.getMeasureUnit());

         SampleContainer anotherSampleContainer = TestSampleContainerBuilder.aSampleContainer()
            .withCapacityMiliLiters(12)
            .withSterilizationMethod(SterilizationMethod.AUTOCLAVE)
            .withClosingMethod(ClosingMethod.SCREW_CAP_ORING)
            .withMaterial(Material.PP)
            .build();

        assertNotNull(anotherSampleContainer);
    }

    @Test
    public void shouldThrowException_WhenCapacityIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            TestSampleContainerBuilder.aSampleContainer().withCapacityMiliLiters(0).build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            TestSampleContainerBuilder.aSampleContainer().withCapacityMiliLiters(100000).build();
        });

        assertEquals("capacity", exception.getInvalidAttribute());
        assertEquals("capacity", exception2.getInvalidAttribute());
    }

    @Test
    public void shouldThrowException_WhenMaterialAndSterilizationMethodIsIncoherent() {
        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            TestSampleContainerBuilder.aSampleContainer()
                .withMaterial(Material.PE)
                .withSterilizationMethod(SterilizationMethod.AUTOCLAVE)
                .build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            TestSampleContainerBuilder.aSampleContainer()
                .withMaterial(Material.PS)
                .withSterilizationMethod(SterilizationMethod.AUTOCLAVE)
                .build();
        }); 

        ArrayList<String> expected = new ArrayList<>();

        expected.add("Supports autoclave");
        expected.add("Sterilization method");

        assertIterableEquals(expected, exception.getInvalidAttributes());
        assertIterableEquals(expected, exception2.getInvalidAttributes());

        assertEquals("SECURITY WARNING: Material and sterilization method (autoclave) has incoherent", exception.getMessage());

        assertEquals(exception.getMessage(), exception2.getMessage());
    }
}
