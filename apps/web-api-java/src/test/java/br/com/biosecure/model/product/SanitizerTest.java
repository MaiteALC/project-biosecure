package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import br.com.biosecure.model.product.Sanitizer.ChemicalBase;
import br.com.biosecure.model.product.Sanitizer.ConcentrationUnit;
import br.com.biosecure.model.product.Sanitizer.PhysicalForm;
import br.com.biosecure.builders.SanitizerBuilder;
import org.junit.jupiter.api.Test;

public class SanitizerTest {
    
    @Test
    public void shouldBuildValidSanitizer() {
        Sanitizer defaultSanitizer = SanitizerBuilder.aSanitizer().build();

        assertNotNull(defaultSanitizer);
        assertEquals(ChemicalBase.QUATERNARY_AMMONIUM, defaultSanitizer.getActiveIngredient());
        assertEquals(1, defaultSanitizer.getConcentration());
        assertEquals(ConcentrationUnit.PERCENTAGE, defaultSanitizer.getConcentrationUnit());

        Sanitizer sanitizer = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(ChemicalBase.ETHANOL)
            .withConcentration(70.0)
            .withPhLevel(6.4)
            .withFlammable(true)
            .withForm(PhysicalForm.GEL)
            .build();

        assertNotNull(sanitizer);
    }

    @Test
    public void shouldThrowException_WhenAlcoholIsHighConcentrationButNotFlammable() {
        String expectedMessage = "SECURITY WARNING: Alcohols with 40% (or more concentration) MUST be flammable.";

        ArrayList<String> expectedInvalidAttributes = new ArrayList<>();

        expectedInvalidAttributes.add("Active ingredient");
        expectedInvalidAttributes.add("Concentration");
        expectedInvalidAttributes.add("Is flammable");

        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            SanitizerBuilder.aSanitizer()
                .withActiveIngredient(Sanitizer.ChemicalBase.ETHANOL)
                .withConcentration(70.0)
                .withFlammable(false) // <--- the mistake
                .build();
        });

        BioSecurityException exception2 = assertThrows(BioSecurityException.class, () -> {
            SanitizerBuilder.aSanitizer()
                .withActiveIngredient(Sanitizer.ChemicalBase.ALCOHOL_ISOPROPYL)
                .withConcentration(99.8)
                .withFlammable(false) // <--- the mistake
                .build();
        });

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage, exception2.getMessage());

        ArrayList<String> invalids = exception.getInvalidAttributes();

        for (int i = 0; i < expectedInvalidAttributes.size(); i++) {
            assertEquals(expectedInvalidAttributes.get(i), invalids.get(i));
        }
    }

    @Test
    public void shouldThrowException_WhenChlorineIsAcidic() {
        String expectedMessage = "SECURITY WARNING: Sodium hypochlorite must be alkaline pH level (>8). Acid pH makes toxic gas.";

        BioSecurityException exception = assertThrows(BioSecurityException.class, () -> {
            SanitizerBuilder.aSanitizer()
                .withActiveIngredient(ChemicalBase.SODIUM_HYPOCHLORITE)
                .withPhLevel(4.0)
                .build();
        });

        assertEquals(expectedMessage, exception.getMessage());

        assertEquals("Active ingredient", exception.getInvalidAttributes().get(0));
        assertEquals("Ph level", exception.getInvalidAttributes().get(1));
    }

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

        InvalidProductAttributeException concentrationException = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withConcentration(1000001)
            .withConcentrationUnit(ConcentrationUnit.PARTS_PER_MILION)
            .build();
        });
        
        InvalidProductAttributeException concentrationException2 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withConcentration(101)
            .withConcentrationUnit(ConcentrationUnit.GAY_LUSSAC) // default chemical base == QUATERNARY_AMMONIUM. This will be invalidate concentration unit and concentration at same time
            .build();
        });
        
        InvalidProductAttributeException concentrationException3 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withConcentration(101)
            .withConcentrationUnit(ConcentrationUnit.PERCENTAGE)
            .build();
        });
        
        InvalidProductAttributeException concentrationException4 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withConcentration(23000001)
            .withConcentrationUnit(ConcentrationUnit.MILIGRAMS_PER_LITER)
            .build();
        });
        
        InvalidProductAttributeException concentrationException5 = assertThrows(InvalidProductAttributeException.class, () -> {
            SanitizerBuilder.aSanitizer()
            .withConcentration(-1)
            .build();
        });

        assertEquals("concentration", concentrationException.getInvalidAttribute());
        assertEquals("[concentration, concentration unit]", concentrationException2.getInvalidAttribute()); 
        assertEquals("concentration", concentrationException3.getInvalidAttribute());
        assertEquals("concentration", concentrationException4.getInvalidAttribute());
        assertEquals("concentration", concentrationException5.getInvalidAttribute());
    }

    @Test
    public void shouldConvertUnitsCorrectly() {
        Sanitizer aSanitizer = SanitizerBuilder.aSanitizer()
            .withConcentration(70)
            .withConcentrationUnit(ConcentrationUnit.GAY_LUSSAC)
            .withActiveIngredient(ChemicalBase.ETHANOL)
            .withFlammable(true)
            .build();

        assertEquals(70, aSanitizer.convertConcentrationUnit(ConcentrationUnit.PERCENTAGE));
        
        
        Sanitizer anotherSanitizer = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(ChemicalBase.PERACETIC_ACID)
            .withConcentration(2000)
            .withConcentrationUnit(ConcentrationUnit.PARTS_PER_MILION)
            .build();

        assertEquals(0.2, anotherSanitizer.convertConcentrationUnit(ConcentrationUnit.PERCENTAGE));
        

        Sanitizer anotherSanitizer2 = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(ChemicalBase.CHLORHEXIDINE)
            .withConcentration(0.5)
            .withDensity(0.93)
            .build();

        assertEquals(4650, anotherSanitizer2.convertConcentrationUnit(ConcentrationUnit.MILIGRAMS_PER_LITER));
        

        Sanitizer anotherSanitizer3 = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(ChemicalBase.CHLORHEXIDINE)
            .withConcentration(4650)
            .withConcentrationUnit(ConcentrationUnit.MILIGRAMS_PER_LITER)
            .withDensity(0.93)
            .build();

        assertEquals(0.5, anotherSanitizer3.convertConcentrationUnit(ConcentrationUnit.PERCENTAGE));
    
        
        Sanitizer anotherSanitizer4 = SanitizerBuilder.aSanitizer()
            .withActiveIngredient(ChemicalBase.ALCOHOL_ISOPROPYL)
            .withConcentration(784000)
            .withDensity(0.8)
            .withConcentrationUnit(ConcentrationUnit.MILIGRAMS_PER_LITER)
            .build();

        assertEquals(98, anotherSanitizer4.convertConcentrationUnit(ConcentrationUnit.PERCENTAGE));
    }
}