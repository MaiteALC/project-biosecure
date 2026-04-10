package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.TaxDataInputDto;
import br.com.biosecure.dto.response.TaxDataResponseDto;
import br.com.biosecure.model.TaxData;

/**
 * A utility class providing static methods to map data between the {@link TaxData}
 * value object and its respective Data Transfer Objects (DTOs).
 *
 * @see TaxData
 * @see TaxDataInputDto
 * @see TaxDataResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class TaxDataMapper {

    /**
     * Maps a {@link TaxDataInputDto} into a valid {@link TaxData} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code TaxData} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws br.com.biosecure.model.InvalidTaxDataException if any field
     * from the DTO fails domain validation during the {@code TaxData} instantiation
     */
    public static TaxData toEntity(TaxDataInputDto dto) {
        if  (dto == null) {
            throw new NullPointerException("A tax data DTO is required");
        }

        return TaxData.builder()
                .lastSearchDate(dto.lastSearchDate())
                .registrationStatusDescription(dto.registrationStatusDescription())
                .registrationStatus(dto.registrationStatus())
                .cnae(CnaeMapper.toEntity(dto.cnae()))
                .activitiesStartDate(dto.activitiesStartDate())
                .build();
    }

    /**
     * Maps a {@link TaxData} entity into a {@link TaxDataResponseDto}.
     *
     * @param entity the domain entity to be mapped
     * @return the resulting DTO
     * @throws NullPointerException if the {@code entity} is null
     */
    public static TaxDataResponseDto toDto(TaxData entity) {
        if (entity == null) {
            throw new NullPointerException("A tax data entity is required");
        }

        return new TaxDataResponseDto(entity.getLastSearchDate(), entity.getActivitiesStartDate(), entity.getRegistrationStatus(), entity.getStatusDescription(), CnaeMapper.toDto(entity.getCnae()));
    }
}
