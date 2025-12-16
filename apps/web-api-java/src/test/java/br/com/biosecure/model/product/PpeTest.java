package br.com.biosecure.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import br.com.biosecure.builders.PpeBuider;
import br.com.biosecure.model.product.PPE.Size;
import br.com.biosecure.model.product.Product.MeasureUnit;

public class PpeTest {
    
    @Test
    public void shouldBuildValidPPE() {
        PPE aPpe = PpeBuider.aPPE()
            .withCertificateOfApproval("C.A.12.549")
            .withDisposable(false)
            .withSize(Size.MEDIUM)
            .build();

        PPE anotherPpe = PpeBuider.aPPE()
            .withCertificateOfApproval("C.A.30206")
            .withSize(Size.UNIVERSAL)
            .build();

        assertEquals(MeasureUnit.UN, aPpe.getMeasureUnit());
        assertEquals(MeasureUnit.UN, anotherPpe.getMeasureUnit());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "CA", "C.A.56789101112"})
    public void shouldThrowException_WhenCertificateOfApprovalIsInvalid(String invalidInput) {
        InvalidProductAttributeException exception = assertThrows(InvalidProductAttributeException.class, () -> {
            PpeBuider.aPPE().withCertificateOfApproval(invalidInput).build();
        });

        assertEquals("certificate of approval", exception.getInvalidAttribute());
        assertTrue(exception.getMessage().contains("Invalid product attributes:\n\t - certificate of approval"));
    }
}
