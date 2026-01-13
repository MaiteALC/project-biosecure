package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import br.com.biosecure.builders.IngredientBuilder;
import br.com.biosecure.model.Ingredient.*;
import br.com.biosecure.model.Sanitizer.PhysicalForm;
import br.com.biosecure.builders.SanitizerBuilder;
import org.junit.jupiter.api.Test;

public class SanitizerTest {
    
    @Test
    public void shouldBuildValidSanitizer() {
        Sanitizer defaultSanitizer = SanitizerBuilder.aSanitizer().build();

        assertNotNull(defaultSanitizer);
        assertEquals(ChemicalFamily.QUATERNARY_AMMONIUM, defaultSanitizer.getMainChemicalFamily());

        Sanitizer sanitizer = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(List.of(IngredientBuilder.anActiveIngredient().build())) // default build of IngredientBuilder is alcohol isopropyl
            .withPhLevel(6.4)
            .withFlammable(true)
            .withForm(PhysicalForm.GEL)
            .build();

        assertNotNull(sanitizer);
    }

//    @Test
//    public void shouldThrowException_WhenAlcoholIsHighConcentrationButNotFlammable() {
//        String expectedMessage = "SECURITY WARNING: Alcohols with 40% (or more concentration) MUST be flammable.";
//
//        ArrayList<String> expectedInvalidAttributes = new ArrayList<>();
//
//        expectedInvalidAttributes.add("Active ingredient");
//        expectedInvalidAttributes.add("Concentration");
//        expectedInvalidAttributes.add("Is flammable");
//
//        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
//            SanitizerBuilder.aSanitizer()
//                .withActiveIngredient(List.of(IngredientBuilder.buildEthanol()))
//                .withFlammable(false) // <--- the mistake
//                .build();
//        });
//
//        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
//            SanitizerBuilder.aSanitizer()
//                .withActiveIngredient(List.of(new Ingredient("Alcohol isopropyl", "67-63-0", ChemicalFamily.ALCOHOL, 99.8, Ingredient.IngredientFunction.ACTIVE_INGREDIENT)))
//                .withFlammable(false) // <--- the mistake
//                .build();
//        });
//
//        assertEquals(expectedMessage, exception.getMessage());
//        assertEquals(expectedMessage, exception2.getMessage());
//
//        ArrayList<String> invalids = exception.getInvalidAttributes();
//
//        for (int i = 0; i < expectedInvalidAttributes.size(); i++) {
//            assertEquals(expectedInvalidAttributes.get(i), invalids.get(i));
//        }
//    }

    @Test
    public void shouldThrowException_WhenProductAttributeIsInvalid() {
        
        InvalidProductAttributeException phException = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withPhLevel(-1)
            .build();
        });
        
        InvalidProductAttributeException phException2 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withPhLevel(15)
            .build();
        });

        assertEquals("ph level", phException.getInvalidAttribute());
        assertEquals("ph level", phException2.getInvalidAttribute());
    }
}