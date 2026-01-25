package br.com.biosecure.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CnaeTest {

    @ParameterizedTest
    @CsvSource({
            "8610-1/01, 8610101",
            "8610-1/02, 8610102",
            "8640-2/02, 8640202",
            "8630-5/01, 8630501"
    })
    public void shouldRemoveFormattingCorrectly(String input, String expected) {
        assertEquals(expected, new Cnae(input).getUnformattedCnaeCode());
    }

    @ParameterizedTest
    @CsvSource({
            "8610-1/01, 8610101",
            "8610-1/02, 8610102",
            "8640-2/02, 8640202",
            "8630-5/01, 8630501"
    })
    public void shouldAddFormattingCorrectly(String expected, String input) {
        assertEquals(expected, new Cnae(input).getFormattedCnaeCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "1234-5/67", "7384-9/01", "3953-2/05", "ajshadsf", "\t"})
    public void shouldInvalidateCnaeCorrectly_WhenItIsNotAllowed(String invalid) {
        assertFalse(Cnae.isAllowedCnae(invalid));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3812-2/00", "3822-0/00", "8129-0/00", "8640-2/02", "8129-0/00"})
    public void shouldValidateCnaeCorrectly_WhenItIsAllowed(String valid) {
        assertTrue(Cnae.isAllowedCnae(new Cnae(valid)));
        assertTrue(Cnae.isAllowedCnae(valid));
    }
}
