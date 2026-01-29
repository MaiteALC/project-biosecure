package br.com.biosecure.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.biosecure.builders.ClientBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ClientTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "test2@gmail.com", "test2@hotmail.com", "test3@outlook.com", "test4@live.com", "test5@yahoo.com", "random text"})
    void shouldThrowException_WhenEmailIsInvalid(String invalidEmail) {
        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
            () -> ClientBuilder.aClient().withEmail(invalidEmail).build()
        );

        assertEquals("email", exception.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"     ", "A", "1", "loooooooooooooooooooooooooooooooooooooooooooooooooong name"})
    void clientCreationMustFailOnName(String invalidName) {
        InvalidClientAttributeException exception = assertThrows(InvalidClientAttributeException.class,
            () -> ClientBuilder.aClient().withCorporateName(invalidName).build()
        );

        assertEquals("name", exception.getInvalidAttribute());
    }
}