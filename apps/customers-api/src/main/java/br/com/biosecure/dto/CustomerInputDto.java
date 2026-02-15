package br.com.biosecure.dto;

import br.com.biosecure.model.Cnpj;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CustomerInputDto(
        @NotBlank(message = "Corporate name is required")
        String corporateName,

        @NotNull(message = "CNPJ number is required")
        Cnpj cnpj,

        @NotBlank(message = "Email is required")
        String email,

        @Valid
        @NotNull
        Set<AddressInputDto> addresses,

        @Valid
        @NotNull
        FinancialDataInputDto financialData,

        @Valid
        @NotNull
        TaxDataInputDto taxData
) {}
