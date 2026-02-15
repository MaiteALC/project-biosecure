package br.com.biosecure.dto;

import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.RegistrationStatus;
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

        @NotNull(message = "CNAE number is required")
        Cnae cnae
) {}
