package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.AddressInputDto;
import br.com.biosecure.dto.response.AddressResponseDto;
import br.com.biosecure.model.Address;

public class AddressMapper {

    public static Address toEntity(AddressInputDto dto) {
        if (dto == null) {
            throw new NullPointerException("An address DTO is required");
        }

        return Address.builder()
                .state(dto.state())
                .city(dto.city())
                .street(dto.street())
                .neighborhood(dto.neighborhood())
                .number(dto.number())
                .postalCode(dto.postalCode())
                .deliveryAddress(dto.deliveryAddress())
                .build();
    }

    public static AddressResponseDto toDto(Address entity) {
        if (entity == null) {
            throw new NullPointerException("An address entity is required");
        }

        return new  AddressResponseDto(entity.getState(), entity.getCity(), entity.getNeighborhood(), entity.getStreet(), entity.getNumber(), entity.getPostalCode(), entity.isDeliveryAddress());
    }
}
