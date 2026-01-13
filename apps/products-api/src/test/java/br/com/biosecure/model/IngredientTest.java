package br.com.biosecure.model;

import br.com.biosecure.builders.IngredientBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @ParameterizedTest
    @ValueSource(ints =  {0, 101, -1, 1000})
    public void shouldThrowException_WhenConcentrationIsInvalid(double concentration) {
        InvalidProductAttributeException concentrationException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientBuilder.anActiveIngredient()
                    .withConcentrationPercentual(concentration)
                    .build();
        });

        assertEquals("concentration quantity", concentrationException.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "\t", "\n", "c", "1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    public void shouldThrowException_WhenNameIsInvalid(String name) {
        InvalidProductAttributeException nameException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientBuilder.anActiveIngredient()
                    .withName(name)
                    .build();
        });

        assertEquals("active ingredient name", nameException.getInvalidAttribute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n", "m", "123", "12345678-99-1", "2222-22-0", "ahasdhafas"})
    public void shouldThrowException_WhenCasNumberIsInvalid(String casNumber) {
        InvalidProductAttributeException casNumberException = assertThrows(InvalidProductAttributeException.class, () -> {
            IngredientBuilder.anActiveIngredient()
                    .withCasNumber(casNumber)
                    .build();
        });

        assertEquals("CAS Registry Number", casNumberException.getInvalidAttribute());
    }

    @ParameterizedTest
    @ValueSource(strings = {"67-63-0", "7732-18-5", "58-08-2", "8042-47-5", "64-17-5", "67-56-1"})
    public void shouldBuildIngredient_WhenCasNumberIsValid(String casNumber) {
       assertDoesNotThrow( () -> {
            IngredientBuilder.anActiveIngredient()
                    .withCasNumber(casNumber)
                    .build();
        });
    }
}
