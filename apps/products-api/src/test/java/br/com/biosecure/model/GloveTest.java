package br.com.biosecure.model;

import br.com.biosecure.builders.GloveTestBuilder;
import br.com.biosecure.model.Glove.GloveMaterial;
import br.com.biosecure.model.PPE.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GloveTest {
    
    @Test
    void shouldBuildValidGlove() {
        Glove aGlove = GloveTestBuilder.aGlove()
            .withMaterial(GloveMaterial.LATEX)
            .withDisposable(true)
            .withPowderFree(true)
            .withSize(Size.SMALL)
            .build();

        Glove defaultGlove = GloveTestBuilder.aGlove().build();

        assertNotNull(aGlove);
        assertNotNull(defaultGlove);
    }

    @Test
    void shouldThrowException_WhenThicknessIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            GloveTestBuilder.aGlove().withThicknessMils(2).build();
        });

        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            GloveTestBuilder.aGlove().withThicknessMils(11).build();
        });

        assertEquals("thickness (mils)", exception.getInvalidAttribute());
        assertEquals(exception.getInvalidAttribute(), exception2.getInvalidAttribute());

        assertTrue(exception.getMessage().contains("These attributes are invalids:\n\t - thickness (mils) |"));
    }
}
