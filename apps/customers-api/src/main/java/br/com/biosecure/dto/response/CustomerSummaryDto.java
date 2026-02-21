package br.com.biosecure.dto.response;

import br.com.biosecure.model.Cnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CustomerSummaryDto(
        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @NotBlank(message = "Email is required")
        String email,

        @NotNull(message = "CNPJ number is required")
        Cnpj cnpj,

        @NotNull(message = "Registration date is required")
        LocalDate registrationDate

) implements CustomerDto {}
