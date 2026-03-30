package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.FinancialDataInputDto;
import br.com.biosecure.dto.response.FinancialDataResponseDto;
import br.com.biosecure.model.FinancialData;

/**
 * A utility class providing static methods to map data between the {@link FinancialData}
 * entity and its respective Data Transfer Objects (DTOs).
 * <p>
 * <strong>Design Principle:</strong> This mapping process acts as an anti-corruption layer
 * between the external API contracts and the internal domain model. By isolating the
 * {@code FinancialData} entity from web-layer concerns, we prevent over-posting vulnerabilities
 * and allow the API payload structures to evolve independently of the core business rules.
 *
 * @see FinancialData
 * @see FinancialDataInputDto
 * @see FinancialDataResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class FinancialDataMapper {

    /**
     * Maps a {@link FinancialDataInputDto} into a valid {@link FinancialData} domain entity.
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code FinancialData} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws br.com.biosecure.model.InvalidFinancialDataException if any field
     * from the DTO fails domain validation during the {@code FinancialData} instantiation
     */
    public static FinancialData toEntity(FinancialDataInputDto dto) {
        if (dto == null) {
            throw new NullPointerException("A financial data DTO is required");
        }

        return new  FinancialData(dto.shareCapital());
    }

    /**
     * Maps a {@link FinancialData} entity into a {@link FinancialDataResponseDto}.
     *
     * @param entity the domain entity to be mapped
     * @return the resulting DTO
     * @throws NullPointerException if the {@code entity} is null
     */
    public static FinancialDataResponseDto toDto(FinancialData entity) {
        if (entity == null) {
            throw new NullPointerException("A financial data entity is required");
        }

        return new FinancialDataResponseDto(entity.getShareCapital(), entity.getTotalCredit(), entity.getUtilizedCredit(), entity.getCustomerId());
    }
}
