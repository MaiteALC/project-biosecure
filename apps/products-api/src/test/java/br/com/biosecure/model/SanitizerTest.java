package br.com.biosecure.model;

import br.com.biosecure.builders.IngredientTestBuilder;
import br.com.biosecure.builders.SanitizerTestBuilder;
import br.com.biosecure.model.Ingredient.ChemicalFamily;
import br.com.biosecure.model.Sanitizer.PhysicalForm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SanitizerTest {
    
    @Test
    void shouldBuildValidSanitizer() {
        Sanitizer defaultSanitizer = SanitizerTestBuilder.aSanitizer().build();

        assertNotNull(defaultSanitizer);
        assertEquals(ChemicalFamily.QUATERNARY_AMMONIUM, defaultSanitizer.getMainChemicalFamily());

        Sanitizer sanitizer = SanitizerTestBuilder.aSanitizer()
            .withActiveIngredient(List.of(IngredientTestBuilder.anActiveIngredient().build())) // default build of IngredientBuilder is alcohol isopropyl
            .withPhLevel(6.4)
            .withFlammable(true)
            .withForm(PhysicalForm.GEL)
            .build();

        assertNotNull(sanitizer);
    }

    @Test
    void shouldThrowException_WhenProductAttributeIsInvalid() {
        
        InvalidProductAttributeException phException = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerTestBuilder.aSanitizer()
            .withPhLevel(-1)
            .build();
        });
        
        InvalidProductAttributeException phException2 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerTestBuilder.aSanitizer()
            .withPhLevel(15)
            .build();
        });

        assertEquals("ph level", phException.getInvalidAttribute());
        assertEquals("ph level", phException2.getInvalidAttribute());
    }
}