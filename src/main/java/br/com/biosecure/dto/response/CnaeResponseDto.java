package br.com.biosecure.dto.response;

import br.com.biosecure.model.Cnae;
import jakarta.validation.constraints.NotBlank;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * {@code GET} operations. It projects the {@link Cnae} value object state
 * into a format optimized for consumption.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param code the plain or formatted 6-digit Brazilian CNAE code
 * @param description the respective CNAE description
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record CnaeResponseDto(
        @NotBlank(message = "CNAE number is required")
        String code,

        @NotBlank(message = "CNAE description is required")
        String description
) {}
