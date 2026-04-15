package br.com.biosecure.dto.response;

import br.com.biosecure.model.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * {@code GET} operations. It projects the {@link Partner} value object state
 * into a format optimized for consumption.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param identifierCode the government-provided partner identifier code
 * @param name the registered partner name
 * @param type the government-provided partner type
 * @param entryDate the entry date of the partner on a company
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record PartnerResponseDto(

        @NotNull(message = "Partner identifier code is required")
        @JsonProperty("identifier_code")
        Integer identifierCode,

        @NotBlank(message = "Partner name is required")
        String name,

        @NotBlank(message = "Partner type is required")
        String type,

        @NotNull(message = "Entry date is required")
        @JsonProperty("entry_date")
        LocalDate entryDate
) {}
