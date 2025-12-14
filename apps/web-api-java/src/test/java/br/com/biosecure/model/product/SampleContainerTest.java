package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.Product.MeasureUnit;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.builders.SampleContainerBuilder;

public class SampleContainerTest {
    
    @Test
    public void shouldCreateValidSampleContainer() {
        SampleContainer aSampleContainer = SampleContainerBuilder.aSampleContainer()
            .withSterilizationMethod(SterilizationMethod.E_BEAM)
            .withClosingMethod(ClosingMethod.HEAT_SEALABLE)
            .withMaterial(Material.PP)
            .build();

        assertNotNull(aSampleContainer);
        assertEquals(MeasureUnit.UN, aSampleContainer.getMeasureUnit());

         SampleContainer anotherSampleContainer = SampleContainerBuilder.aSampleContainer()
            .withSterilizationMethod(SterilizationMethod.AUTOCLAVE)
            .withClosingMethod(ClosingMethod.SCREW_CAP_ORING)
            .withMaterial(Material.PP)
            .build();

        assertNotNull(anotherSampleContainer);
    }

    @Test
    public void shouldThrowException_WhenMaterialAndSterilizationMethodIsIncoherent() {
        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            SampleContainerBuilder.aSampleContainer()
                .withMaterial(Material.PE)
                .withSterilizationMethod(SterilizationMethod.AUTOCLAVE)
                .build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            SampleContainerBuilder.aSampleContainer()
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
