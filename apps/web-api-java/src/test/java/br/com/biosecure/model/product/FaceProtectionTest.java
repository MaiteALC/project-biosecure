package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import br.com.biosecure.builders.FaceProtectionBuilder;
import br.com.biosecure.model.product.FaceProtection.ProtectionType;
import br.com.biosecure.model.product.PPE.Size;

public class FaceProtectionTest {
    
    @Test
    public void shouldBuildValidFaceProtection() {
        FaceProtection aFaceProtection = FaceProtectionBuilder.aFaceProtection()
            .withAntiFog(false)
            .withStandardRating("N95")
            .withType(ProtectionType.MASK_RESPIRATOR)
            .withSize(Size.UNIVERSAL)
            .build();

        FaceProtection anotherFaceProtection = FaceProtectionBuilder.aFaceProtection()
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
    public void shouldThrowException_WhenStandardRatingIsInvalid(String invalidInput) {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            FaceProtectionBuilder.aFaceProtection().withStandardRating(invalidInput).build();
        });

        assertEquals("standard rating", exception.getInvalidAttribute());
        assertTrue(exception.getMessage().contains("Invalid product attributes:\n\t - standard rating"));
    }

    @Test
    public void shouldInferValveAttributeCorrectly() {
        FaceProtection maskProtection = FaceProtectionBuilder.aFaceProtection()
            .withType(ProtectionType.MASK_RESPIRATOR)
            .withValve(true)
            .build();

        FaceProtection gogglesProtection = FaceProtectionBuilder.aFaceProtection()
            .withType(ProtectionType.GOGGLES)
            .withValve(true) // should reject the "true" and set the attribute with "false" to ensure coherence
            .build();

        FaceProtection faceShieldProtection = FaceProtectionBuilder.aFaceProtection()
            .withType(ProtectionType.FACE_SHIELD)
            .withValve(false)
            .build();

        assertTrue(maskProtection.hasValve());
        
        assertFalse(gogglesProtection.hasValve());
        assertFalse(faceShieldProtection.hasValve());
    }
}
