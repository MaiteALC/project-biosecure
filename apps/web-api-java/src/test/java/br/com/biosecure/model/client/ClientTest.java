package br.com.biosecure.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class ClientTest {
    private Address validAddress = new Address("Test State", "Test City", "Test", "Teststreet", 321, "12345-678");
    private Cnpj validCnpj = new Cnpj("53.297.928/0001-46");
    private String validEmail = "teste1@biosecure.com.br";

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "test2@gmail.com", "test2@hotmail.com"})
    public void clientCreationMustFailOnEmail(String invalidEmail) {
        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
            () -> new Client("Zepto Lab", validCnpj, validAddress, invalidEmail)
        );

        assertEquals("email", exception.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"     ", "A", "1"})
    public void clientCreationMustFailOnName(String invalidName) {
        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
            () -> new Client(invalidName, validCnpj, validAddress, validEmail)
        );

        assertEquals("name", exception.getInvalidAttribute());
    }

    @Test
    public void mustFailCnpjValidation() {
        assertThrows(InvalidCnpjException.class, () -> new Cnpj("111111"));

        assertThrows(InvalidCnpjException.class, () -> new Cnpj("  "));

        assertThrows(InvalidCnpjException.class, () -> new Cnpj("12.345.678/0001-99"));
    }

    @Test
    public void mustCreateCnpjWithSuccess() {
        Cnpj valid = new Cnpj("60.316.817/0001-03");

        assertEquals("60316817000103", valid.getNumber());
        assertEquals("60.316.817/0001-03", valid.getFormattedNumber());

        Cnpj valid2 = new Cnpj("69804101000111");

        assertEquals("69804101000111", valid2.getNumber());
        assertEquals("69.804.101/0001-11", valid2.getFormattedNumber());
    }
}