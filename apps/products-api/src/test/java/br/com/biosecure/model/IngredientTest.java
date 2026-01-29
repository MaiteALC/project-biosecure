package br.com.biosecure.model;

import br.com.biosecure.builders.IngredientTestBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @ParameterizedTest
    @ValueSource(ints =  {0, 101, -1, 1000})
    void shouldThrowException_WhenConcentrationIsInvalid(double concentration) {
        InvalidProductAttributeException concentrationException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientTestBuilder.anActiveIngredient()
                    .withConcentrationPercentual(concentration)
                    .build();
        });

        assertEquals("concentration quantity", concentrationException.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "\t", "\n", "c", "1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void shouldThrowException_WhenNameIsInvalid(String name) {
        InvalidProductAttributeException nameException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientTestBuilder.anActiveIngredient()
                    .withName(name)
                    .build();
        });

        assertEquals("active ingredient name", nameException.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n", "m", "123", "12345678-99-1", "2222-22-0", "ahasdhafas"})
    void shouldThrowException_WhenCasNumberIsInvalid(String casNumber) {
        InvalidProductAttributeException casNumberException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientTestBuilder.anActiveIngredient()
                    .withCasNumber(casNumber)
                    .build();
        });

        assertEquals("CAS Registry Number", casNumberException.getInvalidAttribute());
    }

    @ParameterizedTest
    @ValueSource(strings = {"67-63-0", "7732-18-5", "58-08-2", "8042-47-5", "64-17-5", "67-56-1"})
    void shouldBuildIngredient_WhenCasNumberIsValid(String casNumber) {
       assertDoesNotThrow( () -> {
            IngredientTestBuilder.anActiveIngredient()
                    .withCasNumber(casNumber)
                    .build();
        });
    }
}
