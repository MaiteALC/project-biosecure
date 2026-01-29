package br.com.biosecure.model;

import br.com.biosecure.builders.LabCoatTestBuilder;
import br.com.biosecure.model.LabCoat.CollarType;
import br.com.biosecure.model.LabCoat.FabricType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabCoatTest {
    
    @Test
    void shouldBuildValidLabCoat() {
        LabCoat labCoat = LabCoatTestBuilder.aLabCoat()
            .withCollarType(CollarType.HIGH_NECK)
            .withGrammage(30)
            .withDisposable(true)
            .withFabricType(FabricType.POLYPROPYLENE)
            .build();

        LabCoat defaultLabCoat = LabCoatTestBuilder.aLabCoat().build();

        assertNotNull(labCoat);
        assertNotNull(defaultLabCoat);

        assertFalse(defaultLabCoat.isDisposable());
        assertTrue(labCoat.isDisposable());
    }

    @Test
    void shouldThrowException_WhenGrammageIsInvalid() {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            LabCoatTestBuilder.aLabCoat().withGrammage(19).build();
        });

        InvalidProductAttributeException exception2 = assertThrows(InvalidProductAttributeException.class, () -> {
            LabCoatTestBuilder.aLabCoat().withGrammage(351).build();
        });

        assertEquals(exception.getInvalidAttribute(), exception2.getInvalidAttribute());

        assertEquals("grammage (g/cm²)", exception.getInvalidAttribute());

        assertTrue(exception.getMessage().contains("These attributes are invalids:\n\t - grammage (g/cm²) |"));
    }
}
