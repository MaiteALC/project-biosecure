package br.com.biosecure.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record CustomerResponseDto (

        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @NotBlank(message = "CNPJ number is required")
        String cnpj,

        @NotBlank(message = "Email is required")
        String email,

        @Valid
        Set<AddressResponseDto> addresses,

        @Valid
        FinancialDataResponseDto financialData,

        @Valid
        List<TaxDataResponseDto> taxData

) implements CustomerDto {}
