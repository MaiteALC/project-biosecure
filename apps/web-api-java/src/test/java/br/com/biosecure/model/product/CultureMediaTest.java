package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import br.com.biosecure.model.product.CultureMedia.*;
import br.com.biosecure.builders.CultureMediaBuilder;

public class CultureMediaTest {
    
    @Test
    public void shouldBuildValidCultureMedia() {
        CultureMedia aCultureMedia = CultureMediaBuilder.aCultureMediaBuilder()
            .withFinality(CultureMediaFinality.CHROMOGENIC)
            .withPhysicalUnit(PhysicalUnit.PREPARED_LIQUID_BOTTLE)
            .build();

        assertNotNull(aCultureMedia);
        
        CultureMedia anotherCultureMedia = CultureMediaBuilder.aCultureMediaBuilder()
            .withFinalPhLevel(11)
            .withFinality(CultureMediaFinality.ENRICHMENT)
            .withPhysicalUnit(PhysicalUnit.DEHYDRATED_POWDER_BOTTLE)
            .withStorageConditions(StorageConditions.AMBIENT_TEMP)
            .withQuantificationUnit(QuantificationUnit.G)
            .withQuantityPerUnit(70)
            .build();

        assertNotNull(anotherCultureMedia);
    }

    @Test
    public void shouldThrowException_WhenPhLevelIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder().withFinalPhLevel(-1).build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder().withFinalPhLevel(15).build();
        });

        assertEquals("final pH level", exception.getInvalidAttribute());
        assertEquals("final pH level", exception2.getInvalidAttribute());
    }

    @Test
    public void shouldThrowException_WhenPhysicalAndQuantificationUnitIsIncoherent() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder()
                .withPhysicalUnit(PhysicalUnit.DEHYDRATED_POWDER_BOTTLE)
                .withStorageConditions(StorageConditions.AMBIENT_TEMP)
                .withQuantificationUnit(QuantificationUnit.L)
                .build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder()
                .withPhysicalUnit(PhysicalUnit.PREPARED_LIQUID_TUBE)
                .withQuantificationUnit(QuantificationUnit.KG)
                .build();
        });

        assertEquals("quantification unit", exception.getInvalidAttribute());
        assertEquals("quantification unit", exception2.getInvalidAttribute());
    }

    @Test
    public void shouldThrowException_WhenBioSafetyRulesIsViolated() {
        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder()
                .withPhysicalUnit(PhysicalUnit.PREPARED_LIQUID_TUBE)
                .withStorageConditions(StorageConditions.AMBIENT_TEMP)
                .build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            CultureMediaBuilder.aCultureMediaBuilder()
                .withPhysicalUnit(PhysicalUnit.DEHYDRATED_POWDER_SACHET)
                .withStorageConditions(StorageConditions.FROZEN)
                .withQuantificationUnit(QuantificationUnit.MG)
                .build();
        });

        assertEquals("SECURITY WARNING: Preparated plates/tubes/bottles requires refrigeration (8°C or less) to don't contaminate or dry out.", exception.getMessage());

        assertEquals("SECURITY WARNING: Dehydrated powder requires ambient temperature to don't compromise effectiveness.", exception2.getMessage());

        ArrayList<String> expected = new ArrayList<>();

        expected.add("Storage conditions");
        expected.add("Physical unit");

        assertIterableEquals(expected, exception.getInvalidAttributes());
    }
}
