package br.com.biosecure.dto.response;

import br.com.biosecure.model.RegistrationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaxDataResponseDto(
        @NotNull(message = "Last search date is required")
        LocalDateTime lastSearchDate,

        @NotNull(message = "Activities start date is required")
        LocalDate activitiesStartDate,

        @NotNull(message = "Registration status is required")
        RegistrationStatus registrationStatus,

        String statusDescription,

        @Valid
        @NotNull(message = "CNAE is required")
        CnaeResponseDto cnae
) {}
