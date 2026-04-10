package br.com.biosecure.mappers;

import br.com.biosecure.dto.response.PartnerResponseDto;
import br.com.biosecure.external.PartnerExternalDto;
import br.com.biosecure.model.Partner;

/**
 * A utility class providing static methods to map data between the {@link Partner}
 * entity and its respective Data Transfer Objects (DTOs).
 *
 * @see Partner
 * @see PartnerExternalDto
 * @see PartnerResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class PartnerMapper {

    /**
     * Maps a {@link PartnerExternalDto} into a valid {@link Partner} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code Partner} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * from the DTO fails domain validation during the {@code Partner} instantiation
     */
    public static Partner toEntity(PartnerExternalDto dto) {
        if (dto == null) {
            throw new NullPointerException("A partner DTO is required");
        }

        return new Partner(dto.identifierCode(), dto.name(), dto.cpfOrCnpj(), dto.typeCode(), dto.type(), dto.entryDate(), dto.ageRangeCode());
    }

    /**
     * Maps a {@link Partner} entity into a {@link PartnerResponseDto}.
     *
     * @param entity the domain entity to be mapped
     * @return the resulting DTO
     * @throws NullPointerException if the {@code entity} is null
     */
    public static PartnerResponseDto toDto(Partner entity) {
        if (entity == null) {
            throw new NullPointerException("A partner entity is required");
        }

        return new PartnerResponseDto(entity.identifierCode(), entity.name(), entity.type(), entity.entryDate());
    }
}
