package br.com.biosecure.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record CustomerResponseDto (

        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @NotNull(message = "CNPJ number is required")
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
