package br.com.biosecure.model;

import br.com.biosecure.builders.DummyPpe;
import br.com.biosecure.model.PPE.Size;
import br.com.biosecure.model.Product.MeasureUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PpeTest {
    
    @Test
    void shouldBuildValidPPE() {
        PPE aPpe = DummyPpe.builder()
            .certificateOfApproval("C.A.12.549")
            .disposable(false)
            .size(Size.MEDIUM)
            .build();

        PPE anotherPpe = DummyPpe.builder()
            .certificateOfApproval("C.A.30206")
            .size(Size.UNIVERSAL)
            .build();

        assertEquals(MeasureUnit.U, aPpe.getMeasureUnit());
        assertEquals(MeasureUnit.U, anotherPpe.getMeasureUnit());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "CA", "C.A.56789101112"})
    void shouldThrowException_WhenCertificateOfApprovalIsInvalid(String invalidInput) {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> DummyPpe.builder().certificateOfApproval(invalidInput).build());

        assertEquals("certificate of approval", exception.getInvalidAttribute());
        assertTrue(exception.getMessage().contains("These attributes are invalids:\n\t - certificate of approval"));
    }
}
