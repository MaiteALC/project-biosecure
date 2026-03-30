package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.CnaeInputDto;
import br.com.biosecure.dto.response.CnaeResponseDto;
import br.com.biosecure.external.CnaeExternalDto;
import br.com.biosecure.model.Cnae;

/**
 * A utility class providing static methods to map data between the {@link Cnae}
 * value object and its respective Data Transfer Objects (DTOs).
 *
 * @see Cnae
 * @see CnaeInputDto
 * @see CnaeExternalDto
 * @see CnaeResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class CnaeMapper {

    /**
     * Maps a {@link CnaeInputDto} into a valid {@link Cnae} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code Cnae} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws IllegalArgumentException if any field from the DTO
     * fails domain validation during the {@code Cnae} instantiation
     */
    public static Cnae toEntity(CnaeInputDto dto) {
        if (dto == null) {
            throw new NullPointerException("A CNAE input DTO is required");
        }

        return new Cnae(dto.code(), dto.description());
    }

    /**
     * Maps a {@link CnaeExternalDto} into a valid {@link Cnae} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code Cnae} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws IllegalArgumentException if any field from the DTO
     * fails domain validation during the {@code Cnae} instantiation
     */
    public static Cnae toEntity(CnaeExternalDto dto) {
        if (dto == null) {
            throw new NullPointerException("A CNAE external DTO is required");
        }

        return new Cnae(dto.code(), dto.description());
    }

    /**
     * Maps a {@link Cnae} entity into a {@link CnaeResponseDto}.
     *
     * @param entity the domain entity to be mapped
     * @return the resulting DTO
     * @throws NullPointerException if the {@code entity} is null
     */
    public static CnaeResponseDto toDto(Cnae entity) {
        if (entity == null) {
            throw new NullPointerException("A CNAE entity is required");
        }

        return new CnaeResponseDto(entity.getFormattedCode(), entity.getDescription());
    }
}
