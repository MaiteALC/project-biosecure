package br.com.biosecure.mappers;

import br.com.biosecure.builders.AddressTestBuilder;
import br.com.biosecure.dto.AddressInputDto;
import br.com.biosecure.dto.AddressResponseDto;
import br.com.biosecure.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressMapperTest {

    @Test
    void shouldConvertToAddressDtoCorrectly() {
        Address address = AddressTestBuilder.anAddress().withState("MG").withCity("Belo Horizonte").build();

        AddressResponseDto dto = AddressMapper.toDto(address);

        assertEquals("MG", dto.state());
        assertEquals("Belo Horizonte", dto.city());
    }

    @Test
    void shouldConvertToAddressEntityCorrectly() {
        AddressInputDto dto = new AddressInputDto("BA", "Salvador", "Matatu", "Rua Barros Falcao", "284", "44444-444", false);

        Address address = AddressMapper.toEntity(dto);

        assertEquals(dto.state(), address.getState());
        assertEquals(dto.city(), address.getCity());
        assertEquals(dto.postalCode(), address.getPostalCode());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> AddressMapper.toDto(null));

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> AddressMapper.toEntity(null));

        assertEquals("An address entity is required", exception.getMessage());
        assertEquals("An address DTO is required", exception2.getMessage());
    }
}
