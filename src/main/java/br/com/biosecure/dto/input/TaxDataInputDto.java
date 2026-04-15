package br.com.biosecure.dto.input;

import br.com.biosecure.model.RegistrationStatus;
import br.com.biosecure.model.TaxData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Data Transfer Object (DTO) designed to encapsulate input data from API request payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the primary contract for
 * {@code POST} requests. It securely transports raw input data from the web layer to the
 * service layer, where it is used to instantiate the {@link TaxData} value object.
 * <p>
 * <strong>Validation:</strong> It enforces strict input constraints using standard
 * annotations, acting as the first line of defense by rejecting malformed or missing
 * mandatory fields before they reach the domain logic.
 *
 * @param lastSearchDate The ISO-8601 formatted date (YYYY-MM-DD) representing the last data these data was reviewed
 * @param activitiesStartDate The ISO-8601 formatted date (YYYY-MM-DD) representing the customer activities start date
 * @param registrationStatus the legal registration status; see {@link RegistrationStatus} for valid options
 * @param registrationStatusDescription a description for the provided registration status; can be blank
 * @param cnae the nested Brazilian CNAE information, required for
 *
 * @see CnaeInputDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record TaxDataInputDto(

        @NotNull(message = "last search date is required")
        @JsonProperty("last_search_date")
        LocalDateTime lastSearchDate,

        @NotNull(message = "activities start date is required")
        @JsonProperty("activities_start_date")
        LocalDate activitiesStartDate,

        @NotNull(message = "registration status is required")
        @JsonProperty("registration_status")
        RegistrationStatus registrationStatus,

        @JsonProperty("registration_status_description")
        String registrationStatusDescription,

        @Valid
        @NotNull(message = "CNAE is required")
        CnaeInputDto cnae
) {}
