package br.com.biosecure.model;

import br.com.biosecure.builders.AddressTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    @Test
    void shouldBuildValidAddress() {
        AddressTestBuilder.anAddress().build();

        Address eifelTower = AddressTestBuilder.anAddress()
                .withState("Île-de-France") // France don't have states like Brazil, but whatever, it's a test
                .withCity("Paris")
                .withNeighborhood("7º arrondissement")
                .withStreet("5 Avenue Anatole France")
                .withNumber("75007")
                .withPostalCode("21831-024") // random number. Paris don't have postal codes in same format than Brazil
                .build();

        System.out.println(eifelTower);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "a", "197", "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong string"})
    void shouldThrowException_WhenAddressStringAttributesIsInvalid(String invalid) {
        assertThrows(InvalidAddressException.class, // all of this attributes fail in the same way
                () -> AddressTestBuilder.anAddress()
                        .withState(invalid)
                        .withCity(invalid)
                        .withNeighborhood(invalid)
                        .withStreet(invalid)
                        .withPostalCode(invalid)
                        .build()
        );
    }
}
