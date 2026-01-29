package br.com.biosecure.model;

import br.com.biosecure.builders.FaceProtectionTestBuilder;
import br.com.biosecure.model.FaceProtection.ProtectionType;
import br.com.biosecure.model.PPE.Size;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FaceProtectionTest {
    
    @Test
    void shouldBuildValidFaceProtection() {
        FaceProtection aFaceProtection = FaceProtectionTestBuilder.aFaceProtection()
            .withAntiFog(false)
            .withStandardRating("N95")
            .withType(ProtectionType.MASK_RESPIRATOR)
            .withSize(Size.UNIVERSAL)
            .build();

        FaceProtection anotherFaceProtection = FaceProtectionTestBuilder.aFaceProtection()
            .withAntiFog(true)
            .withSize(Size.UNIVERSAL)
            .withType(ProtectionType.SAFETY_GLASSES)
            .withStandardRating("ANSI Z87.1")
            .build();

        assertNotNull(aFaceProtection);
        assertNotNull(anotherFaceProtection);

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "N", "N1234567890123"})
    void shouldThrowException_WhenStandardRatingIsInvalid(String invalidInput) {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            FaceProtectionTestBuilder.aFaceProtection().withStandardRating(invalidInput).build();
        });

        assertEquals("standard rating", exception.getInvalidAttribute());
        assertTrue(exception.getMessage().contains("These attributes are invalids:\n\t - standard rating"));
    }

    @Test
    void shouldInferValveAttributeCorrectly() {
        FaceProtection maskProtection = FaceProtectionTestBuilder.aFaceProtection()
            .withType(ProtectionType.MASK_RESPIRATOR)
            .withValve(true)
            .build();

        FaceProtection gogglesProtection = FaceProtectionTestBuilder.aFaceProtection()
            .withType(ProtectionType.GOGGLES)
            .withValve(true) // should reject the "true" and set the attribute with "false" to ensure coherence
            .build();

        FaceProtection faceShieldProtection = FaceProtectionTestBuilder.aFaceProtection()
            .withType(ProtectionType.FACE_SHIELD)
            .withValve(false)
            .build();

        assertTrue(maskProtection.isHasValve());
        
        assertFalse(gogglesProtection.isHasValve());
        assertFalse(faceShieldProtection.isHasValve());
    }
}
