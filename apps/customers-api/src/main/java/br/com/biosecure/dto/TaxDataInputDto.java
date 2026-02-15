package br.com.biosecure.dto;

import br.com.biosecure.model.RegistrationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaxDataInputDto(

        @NotNull(message = "last search date is required")
        LocalDateTime lastSearchDate,

        @NotNull(message = "activities start date is required")
        LocalDate activitiesStartDate,

        @NotNull(message = "registration status is required")
        RegistrationStatus registrationStatus,

        String registrationStatusDescription,

        @Valid
        @NotNull(message = "CNAE is required")
        CnaeInputDto cnae
) {}
