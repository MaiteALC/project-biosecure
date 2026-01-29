package br.com.biosecure.model;

import br.com.biosecure.builders.DummySampleContainer;
import br.com.biosecure.model.Product.MeasureUnit;
import br.com.biosecure.model.SampleContainer.ClosingMethod;
import br.com.biosecure.model.SampleContainer.Material;
import br.com.biosecure.model.SampleContainer.SterilizationMethod;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SampleContainerTest {
    
    @Test
    void shouldCreateValidSampleContainer() {
        SampleContainer aSampleContainer = DummySampleContainer.builder()
            .sterilizationMethod(SterilizationMethod.E_BEAM)
            .closingMethod(ClosingMethod.HEAT_SEALABLE)
            .materialType(Material.PP)
            .build();

        assertNotNull(aSampleContainer);
        assertEquals(MeasureUnit.U, aSampleContainer.getMeasureUnit());

         SampleContainer anotherSampleContainer = DummySampleContainer.builder()
            .sterilizationMethod(SterilizationMethod.AUTOCLAVE)
            .closingMethod(ClosingMethod.SCREW_CAP_RING)
            .materialType(Material.PP)
            .build();

        assertNotNull(anotherSampleContainer);
    }

    @Test
    void shouldThrowException_WhenMaterialAndSterilizationMethodIsIncoherent() {
        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> DummySampleContainer.builder()
            .materialType(Material.PE)
            .sterilizationMethod(SterilizationMethod.AUTOCLAVE)
            .build());
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> DummySampleContainer.builder()
            .materialType(Material.PS)
            .sterilizationMethod(SterilizationMethod.AUTOCLAVE)
            .build());

        ArrayList<String> expected = new ArrayList<>();

        expected.add("Supports autoclave");
        expected.add("Sterilization method");

        assertIterableEquals(expected, exception.getInvalidAttributes());
        assertIterableEquals(expected, exception2.getInvalidAttributes());

        assertEquals("SECURITY WARNING: Material and sterilization method (autoclave) has incoherent", exception.getMessage());

        assertEquals(exception.getMessage(), exception2.getMessage());
    }
}
