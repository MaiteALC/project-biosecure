package br.com.biosecure.model;

import br.com.biosecure.builders.CultureMediaTestBuilder;
import br.com.biosecure.model.CultureMedia.CultureMediaFinality;
import br.com.biosecure.model.CultureMedia.Presentation;
import br.com.biosecure.model.CultureMedia.QuantificationUnit;
import br.com.biosecure.model.CultureMedia.StorageConditions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class CultureMediaTest {
    
    @Test
    void shouldBuildValidCultureMedia() {
        CultureMedia aCultureMedia = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withFinality(CultureMediaFinality.CHROMOGENIC)
            .withPresentationForm(Presentation.PREPARED_LIQUID_BOTTLE)
            .withStorageConditions(StorageConditions.FROZEN)
            .build();

        assertNotNull(aCultureMedia);
        
        CultureMedia anotherCultureMedia = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withFinalPhLevel(11)
            .withFinality(CultureMediaFinality.ENRICHMENT)
            .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
            .withStorageConditions(StorageConditions.AMBIENT_TEMP)
            .withQuantificationUnit(QuantificationUnit.G)
            .withQuantityPerUnit(70)
            .build();

        assertNotNull(anotherCultureMedia);
    }

    @Test
    void shouldThrowException_WhenPhLevelIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder().withFinalPhLevel(-1).build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder().withFinalPhLevel(15).build();
        });

        assertEquals("final pH level", exception.getInvalidAttribute());
        assertEquals("final pH level", exception2.getInvalidAttribute());
    }

    @Test
    void shouldThrowException_WhenPresentationAndQuantificationUnitIsIncoherent() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
                .withStorageConditions(StorageConditions.AMBIENT_TEMP)
                .withQuantificationUnit(QuantificationUnit.L)
                .build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.PREPARED_LIQUID_TUBE)
                .withQuantificationUnit(QuantificationUnit.KG)
                .build();
        });

        assertEquals("quantification unit/presentation form", exception.getInvalidAttribute());
        assertEquals("quantification unit/presentation form", exception2.getInvalidAttribute());
    }

    @Test
    void shouldThrowException_WhenBioSafetyRulesIsViolated() {
        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.PREPARED_LIQUID_TUBE)
                .withStorageConditions(StorageConditions.AMBIENT_TEMP)
                .build();
        });
        
        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_SACHET)
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

    @Test
    void shouldValidateCorrectly_WhenQuantityPreparationIsNecessary() {
        CultureMedia needsQuantityPrep = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
            .withQuantificationUnit(QuantificationUnit.MG)
            .withPreparationGramsPerLiter(OptionalDouble.of(3.5))
            .withStorageConditions(StorageConditions.FRESH)
            .build();
        
        CultureMedia needsQuantityPrep2 = CultureMediaTestBuilder.aCultureMediaBuilder()
            .withPresentationForm(Presentation.DEHYDRATED_POWDER_SACHET)
            .withQuantificationUnit(QuantificationUnit.MG)
            .withPreparationGramsPerLiter(OptionalDouble.of(5.0))
            .withStorageConditions(StorageConditions.FRESH)
            .build();

        assertEquals(3.5, needsQuantityPrep.getPreparationGramsPerLiter().getAsDouble());
        assertEquals(5.0, needsQuantityPrep2.getPreparationGramsPerLiter().getAsDouble());
    }
    
    @Test
    void shouldRejectInvalidValues_WhenQuantityPreparationIsNecessary() {
        String expected = "quantity for preparation (g/L)";

        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
                .withQuantificationUnit(QuantificationUnit.MG)
                .withPreparationGramsPerLiter(OptionalDouble.of(0))
                .withStorageConditions(StorageConditions.FRESH)
                .build();
        });
        
        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
                .withQuantificationUnit(QuantificationUnit.MG)
                .withStorageConditions(StorageConditions.FRESH)
                .withPreparationGramsPerLiter(OptionalDouble.of(1001))
                .build();
        });
        
        InvalidProductAttributeException exception3 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
                .withQuantificationUnit(QuantificationUnit.MG)
                .withStorageConditions(StorageConditions.FRESH)
                .withPreparationGramsPerLiter(null)
                .build();
        });
        
        InvalidProductAttributeException exception4 = assertThrows(InvalidProductAttributeException.class, () -> {
            CultureMediaTestBuilder.aCultureMediaBuilder()
                .withPresentationForm(Presentation.DEHYDRATED_POWDER_BOTTLE)
                .withQuantificationUnit(QuantificationUnit.MG)
                .withStorageConditions(StorageConditions.FRESH)
                .withPreparationGramsPerLiter(OptionalDouble.empty())
                .build();
        });

        assertEquals(expected, exception.getInvalidAttribute());
        assertEquals(expected, exception2.getInvalidAttribute());
        assertEquals(expected, exception3.getInvalidAttribute());
        assertEquals(expected, exception4.getInvalidAttribute());
    }
}
