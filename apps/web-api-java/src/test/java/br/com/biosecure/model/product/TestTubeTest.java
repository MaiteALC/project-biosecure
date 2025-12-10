package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.SampleContainer.*;
import br.com.biosecure.model.product.TestTube.*;
import br.com.biosecure.utils.TestTubeBuilder;

public class TestTubeTest {
    
    @Test
    public void shouldBuildValidTestTube() {
        TestTube aTube = TestTubeBuilder.aTestTube()
            .withBottomType(BottomType.ROUND)
            .withCapColor(CapColor.PURPLE)
            .withMaterial(Material.PP)
            .withMaxRCF(6000)
            .withClosingMethod(ClosingMethod.SNAP_CAP)
            .withGraduated(true)
            .build();

        assertNotNull(aTube);

        TestTube anotherTube = TestTubeBuilder.aTestTube()
            .withBottomType(BottomType.FLAT)
            .withMaterial(Material.PS)
            .withMaxRCF(4000)
            .withSterilizationMethod(SterilizationMethod.GAMMA_RAYS)
            .build();

        assertNotNull(anotherTube);
    }

    @Test
    public void shouldThrowException_WhenPhysicalDimensionsIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            TestTubeBuilder.aTestTube().withDiameterMm(0).withHeightMm(0).build();
        });

        assertEquals("physical dimensions", exception.getInvalidAttribute());
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            TestTubeBuilder.aTestTube().withDiameterMm(1000).withHeightMm(1000).build();
        });

        assertEquals("physical dimensions", exception2.getInvalidAttribute());
    }

    @Test
    public void shouldThrowException_WhenBioSafetyRulesIsViolated() {
        String expectedMessage = "SECURITY WARNING: Glass tubes rarely supports RCF greater than 5000g. Verify the specifications.";
        
        String expectedMessage2 = "SECURITY WARNING: Flat bottoms concentrate the tension. RCF greater than 10.000g is uncommon for flat bottoms. Verify the specifications";

        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            TestTubeBuilder.aTestTube()
                .withMaterial(Material.BOROSILICATE_GLASS)
                .withMaxRCF(5001)
                .build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            TestTubeBuilder.aTestTube()
                .withMaterial(Material.PC)
                .withBottomType(BottomType.FLAT)
                .withMaxRCF(10001)
                .build();
        });

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage2, exception2.getMessage());

        assertEquals("Maximum RCF", exception.getInvalidAttributes().get(0));
        assertEquals("Maximum RCF", exception2.getInvalidAttributes().get(0));

        assertEquals("Material", exception.getInvalidAttributes().get(1));
        assertEquals("Bottom type", exception2.getInvalidAttributes().get(1));
    }

    @Test
    public void shouldCalculateNominalCapacityCorrectly() {
        double delta = 0.01;

        assertEquals(47.12, TestTube.calculateNominalCapacity(20, 150), delta);
        
        assertEquals(17.67, TestTube.calculateNominalCapacity(15, 100), delta);
        
        assertEquals(11.31, TestTube.calculateNominalCapacity(12, 100), delta);
    }

    @Test
    public void shouldThrowExecption_WhenCalculateNominalCapacity_GivenInvalidPhysicalDimensions() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            TestTube.calculateNominalCapacity(0, 0);
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            TestTube.calculateNominalCapacity(1000, 1000);
        });

        assertEquals("physical dimensions", exception.getInvalidAttribute());
        assertEquals(exception.getInvalidAttribute(), exception2.getInvalidAttribute());
    }
}
