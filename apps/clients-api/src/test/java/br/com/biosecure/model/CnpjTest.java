package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CnpjTest {

    @Test
    public void mustFailInCnpjValidation() {
        InvalidCnpjException formatException =  assertThrows(InvalidCnpjException.class, () -> new Cnpj("111111"));
        InvalidCnpjException formatException2 =  assertThrows(InvalidCnpjException.class, () -> new Cnpj("1111111111111111111"));

        InvalidCnpjException blankException = assertThrows(InvalidCnpjException.class, () -> new Cnpj("  "));

        InvalidCnpjException nullException = assertThrows(InvalidCnpjException.class, () -> new Cnpj(null));

        InvalidCnpjException validationException = assertThrows(InvalidCnpjException.class, () -> new Cnpj("12.345.678/0001-99"));

        assertEquals(blankException.getMessage(), nullException.getMessage());
        assertEquals("CNPJ number is null/blank", nullException.getMessage());

        assertEquals("CNPJ with invalid format", formatException.getMessage());
        assertEquals("CNPJ with invalid format", formatException2.getMessage());

        assertEquals("Invalid verifier digits", validationException.getMessage());
    }

    @Test
    public void mustValidateCnpjWithSuccess() {
        Cnpj valid = new Cnpj("60.316.817/0001-03");

        assertEquals("60316817000103", valid.getNumber());
        assertEquals("60.316.817/0001-03", valid.getFormattedNumber());

        Cnpj valid2 = new Cnpj("69804101000111");

        assertEquals("69804101000111", valid2.getNumber());
        assertEquals("69.804.101/0001-11", valid2.getFormattedNumber());
    }
}
