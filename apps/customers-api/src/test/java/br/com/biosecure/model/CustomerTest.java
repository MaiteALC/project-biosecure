package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.biosecure.builders.CustomerTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

class CustomerTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "test2@gmail.com", "test2@hotmail.com", "test3@outlook.com", "test4@live.com", "test5@yahoo.com", "random text"})
    void shouldThrowException_WhenEmailIsInvalid(String invalidEmail) {
        InvalidCustomerAttributeException exception = assertThrows(InvalidCustomerAttributeException.class,
            () -> CustomerTestBuilder.aCustomer().withEmail(invalidEmail).build()
        );

        assertEquals("email", exception.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"     ", "A", "1", "looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong name"})
    void shouldThrowException_WhenNameIsInvalid(String invalidName) {
        InvalidCustomerAttributeException exception = assertThrows(InvalidCustomerAttributeException.class,
            () -> CustomerTestBuilder.aCustomer().withCorporateName(invalidName).build()
        );

        assertEquals("name", exception.getInvalidAttribute());
    }

    @Test
    void shouldThrowException_WhenAddressIsInvalid() {
        Set<Address> addresses = new HashSet<>();

        InvalidCustomerAttributeException exception = assertThrows(InvalidCustomerAttributeException.class, () -> CustomerTestBuilder.aCustomer()
                .withAddress(addresses)
                .build()
        );

       assertEquals("These attributes are invalids:\n\t - addresses | at least one address is required\n", exception.getMessage());
    }
}