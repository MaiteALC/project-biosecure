package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.SampleBag.FilterType;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.builders.SampleBagBuilder;

public class SampleBagTest {
    
    @Test
    public void shouldBuildValidSampleBag() {
        SampleBag aBag = SampleBagBuilder.aSampleBag()
            .withFilter(FilterType.NONE)
            .withIdentificationTag(true)
            .withMaterial(Material.PE)
            .build();

        SampleBag anotherBag = SampleBagBuilder.aSampleBag()
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
    public void shouldThrowException_WhenBioSafetyRulesIsViolated() {
        final String begginingOfMessage = "SECURITY WARNING: Sample bags must be of flexible material (PE, PP).";

        BioSecurityException excepiton = assertThrows(BioSecurityException.class, () -> {
            SampleBagBuilder.aSampleBag().withMaterial(Material.BOROSILICATE_GLASS).build();
        });
        
        BioSecurityException excepiton2 = assertThrows(BioSecurityException.class, () -> {
            SampleBagBuilder.aSampleBag().withMaterial(Material.PC).build();
        });
        
        BioSecurityException excepiton3 = assertThrows(BioSecurityException.class, () -> {
            SampleBagBuilder.aSampleBag().withMaterial(Material.PS).build();
        });

        assertEquals(begginingOfMessage + " Borosilicate Glass is rigid.", excepiton.getMessage());

        assertEquals(begginingOfMessage + " Polycarbonate is rigid.", excepiton2.getMessage());

        assertEquals(begginingOfMessage + " Polystyrene is rigid.", excepiton3.getMessage());
    }

    @Test
    public void shouldThrowException_WhenPhysicalDimensionsIsInvalid() {
        String expectedMsg = "Invalid product attributes:\n\t - heigth (mm) | The number is greater than allowed\n\t - width (mm) | The number is greater than allowed\n\t - thickness (mm) | The number is greater than allowed\n\t - capacity (mL) | The number is greater than allowed\n";

        String expectedInvalidAttributeString = "[heigth (mm), width (mm), thickness (mm), capacity (mL)]";

        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            SampleBagBuilder.aSampleBag()
                .withCapacityMiliLiters(10000)
                .withHeightMm(1000)
                .withWidthMm(1000)
                .withThicknessMm(100)
                .build();
        });

        assertEquals(expectedMsg, exception.getMessage());
        assertEquals(expectedInvalidAttributeString, exception.getInvalidAttribute());
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            SampleBagBuilder.aSampleBag()
                .withCapacityMiliLiters(0)
                .withHeightMm(0)
                .withWidthMm(0)
                .withThicknessMm(0)
                .build();
        });

        assertEquals(expectedMsg.replace("greater", "less"), exception2.getMessage());
        assertEquals(expectedInvalidAttributeString, exception2.getInvalidAttribute());
    }
}
