package br.com.biosecure.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record CustomerResponseDto (

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Registration date is required")
        LocalDate registrationDate,

        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @NotBlank(message = "CNPJ number is required")
        String cnpj,

        @NotBlank(message = "Email is required")
        String email,

        @Valid
        @NotNull
        Set<AddressResponseDto> addresses,

        @Valid
        @NotNull
        FinancialDataResponseDto financialData,

        @Valid
        @NotNull
        List<TaxDataResponseDto> taxData

) implements CustomerDto {}
