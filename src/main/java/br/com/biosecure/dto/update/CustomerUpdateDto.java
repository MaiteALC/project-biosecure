package br.com.biosecure.dto.update;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CustomerUpdateDto (
    @NotNull
    UUID customerId,

    String corporateName,

    String email,

    Set<AddressUpdateDto> addresses,

    FinancialDataUpdateDto financialData,

    TaxDataUpdateDto taxData
) {}
