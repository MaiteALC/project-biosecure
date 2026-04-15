package br.com.biosecure.dto.response;

import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.RegistrationStatus;
import br.com.biosecure.model.TaxData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * {@code GET} operations. It projects the {@link TaxData} value object state
 * into a format optimized for consumption.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param lastSearchDate the ISO-8601 last search date for this tax data (YYYY-MM-DD)
 * @param activitiesStartDate The ISO-8601 formatted date (YYYY-MM-DD) representing the customer activities start date
 * @param registrationStatus the legal registration status; see {@link RegistrationStatus} for valid options
 * @param statusDescription the description for the respective registration status; may be null/blank
 * @param cnae the Brazilian CNAE information; see {@link Cnae} for more details
 *
 * @see CnaeResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record TaxDataResponseDto(
        @NotNull(message = "Last search date is required")
        @JsonProperty("last_search_date")
        LocalDateTime lastSearchDate,

        @NotNull(message = "Activities start date is required")
        @JsonProperty("activities_start_date")
        LocalDate activitiesStartDate,

        @NotNull(message = "Registration status is required")
        @JsonProperty("registration_status")
        RegistrationStatus registrationStatus,

        @JsonProperty("status_description")
        String statusDescription,

        @Valid
        @NotNull(message = "CNAE is required")
        CnaeResponseDto cnae
) {}
