package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import br.com.biosecure.model.SampleBag.FilterType;
import br.com.biosecure.model.SampleContainer.*;
import br.com.biosecure.builders.SampleBagTestBuilder;

class SampleBagTest {
    
    @Test
    void shouldBuildValidSampleBag() {
        SampleBag aBag = SampleBagTestBuilder.aSampleBag()
            .withFilter(FilterType.NONE)
            .withIdentificationTag(true)
            .withMaterial(Material.PE)
            .build();

        SampleBag anotherBag = SampleBagTestBuilder.aSampleBag()
            .withMaterial(Material.PP)
            .withClosingMethod(ClosingMethod.WIRE_TAB)
            .withIdentificationTag(false)
            .withSterilizationMethod(SterilizationMethod.ETHYLENE_OXIDE)
            .withThicknessMm(5)
            .build();

        assertNotNull(aBag);
        assertNotNull(anotherBag);
    }

    @Test
    void shouldThrowException_WhenBioSafetyRulesIsViolated() {
        final String beginningOfMessage = "SECURITY WARNING: Sample bags must be of flexible material (PE, PP).";

        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            SampleBagTestBuilder.aSampleBag().withMaterial(Material.BOROSILICATE_GLASS).build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            SampleBagTestBuilder.aSampleBag().withMaterial(Material.PC).build();
        });
        
        BioSecurityException exception3 = assertThrows(BioSecurityException.class, () -> {
            SampleBagTestBuilder.aSampleBag().withMaterial(Material.PS).build();
        });

        assertEquals(beginningOfMessage + " Borosilicate Glass is rigid.", exception.getMessage());

        assertEquals(beginningOfMessage + " Polycarbonate is rigid.", exception2.getMessage());

        assertEquals(beginningOfMessage + " Polystyrene is rigid.", exception3.getMessage());
    }

    @Test
    void shouldThrowException_WhenPhysicalDimensionsIsInvalid() {
        String expectedMsg = "These attributes are invalids:\n\t - height (mm) | The number is greater than allowed\n\t - width (mm) | The number is greater than allowed\n\t - thickness (mm) | The number is greater than allowed\n\t - capacity (mL) | The number is greater than allowed\n";

        String expectedInvalidAttributeString = "[height (mm), width (mm), thickness (mm), capacity (mL)]";

        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            SampleBagTestBuilder.aSampleBag()
                .withCapacityMilliLiters(10000)
                .withHeightMm(1000)
                .withWidthMm(1000)
                .withThicknessMm(100)
                .build();
        });

        assertEquals(expectedMsg, exception.getMessage());
        assertEquals(expectedInvalidAttributeString, exception.getInvalidAttribute());
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            SampleBagTestBuilder.aSampleBag()
                .withCapacityMilliLiters(0)
                .withHeightMm(0)
                .withWidthMm(0)
                .withThicknessMm(0)
                .build();
        });

        assertEquals(expectedMsg.replace("greater", "less"), exception2.getMessage());
        assertEquals(expectedInvalidAttributeString, exception2.getInvalidAttribute());
    }
}
