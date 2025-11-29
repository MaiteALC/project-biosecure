package br.com.biosecure.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClientTest {
    Address validAddress = new Address("Test State", "Test City", "Test", "Teststreet", 321, "12345-678");

    Cnpj validCnpj = new Cnpj("53.297.928/0001-46");

    @Test
    @DisplayName("The creation of client don't be allowed when te email isn't valid")
    public void clientCreationMustFailOnEmail() {
        String invalidEmail = "  ";
        String invalidEmail2 = "test2@gmail.com";
        String invalidEmail3 = "test2@hotmail.com";
        
        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
                () -> new Client("Microsoft", validCnpj, validAddress, invalidEmail)
        );

        assertEquals("email", exception.getInvalidAttribute());
        
        InvalidClientAttributeException exception2 = assertThrows(InvalidClientAttributeException.class,
                () -> new Client("BioSecure", validCnpj, validAddress, invalidEmail2)
        );

        assertEquals("email", exception2.getInvalidAttribute());

        InvalidClientAttributeException exception3 = assertThrows(InvalidClientAttributeException.class,
                () -> new Client("Zepto Lab", validCnpj, validAddress, invalidEmail3)
        );

        assertEquals("email", exception3.getInvalidAttribute());
    }

    @Test
    @DisplayName("Creation of client don't be allowed when the name is invalid (blank)")
    public void clientCreationMustFailOnName() {
        String validEmail = "teste1@biosecure.com.br";

        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
                () -> new Client("  ", validCnpj, validAddress, validEmail)
        );

        assertEquals("corporate name", exception.getInvalidAttribute());
    }

    @Test
    @DisplayName("The creation of these CNPJs must be fail because they are invalids")
    public void mustFailCnpjValidation() {
        assertThrows(InvalidCnpjException.class, () -> new Cnpj("111111"));

        assertThrows(InvalidCnpjException.class, () -> new Cnpj("  "));

        assertThrows(InvalidCnpjException.class, () -> new Cnpj("12.345.678/0001-99"));
    }

    @Test
    @DisplayName("The creation of these CNPJs mustn't be fail because all of them are valids")
    public void mustCreateCnpjWithSuccess() {
        Cnpj valid = new Cnpj("60.316.817/0001-03");

        assertEquals("60316817000103", valid.getNumber());
        assertEquals("60.316.817/0001-03", valid.getFormattedNumber());

        Cnpj valid2 = new Cnpj("69804101000111");

        assertEquals("69804101000111", valid2.getNumber());
        assertEquals("69.804.101/0001-11", valid2.getFormattedNumber());
    }
}