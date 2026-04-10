package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.AddressInputDto;
import br.com.biosecure.dto.response.AddressResponseDto;
import br.com.biosecure.model.Address;

/**
 * A utility class providing static methods to map data between the {@link Address}
 * value object and its respective Data Transfer Objects (DTOs).
 *
 * @see Address
 * @see AddressInputDto
 * @see AddressResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class AddressMapper {

    /**
     * Maps a {@link AddressInputDto} into a valid {@link Address} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code Address} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws br.com.biosecure.model.InvalidAddressException if any field
     * from the DTO fails domain validation during the {@code Address} instantiation
     */
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

    /**
     * Maps a {@link Address} entity into a {@link AddressResponseDto}.
     *
     * @param entity the domain entity to be mapped
     * @return the resulting DTO
     * @throws NullPointerException if the {@code entity} is null
     */
    public static AddressResponseDto toDto(Address entity) {
        if (entity == null) {
            throw new NullPointerException("An address entity is required");
        }

        return new  AddressResponseDto(entity.getState(), entity.getCity(), entity.getNeighborhood(), entity.getStreet(), entity.getNumber(), entity.getPostalCode(), entity.isDeliveryAddress());
    }
}
