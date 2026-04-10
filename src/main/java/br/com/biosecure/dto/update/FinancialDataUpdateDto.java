package br.com.biosecure.dto.update;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record FinancialDataUpdateDto(

        @NotNull
        UUID customerId,

        BigDecimal shareCapital,

        BigDecimal totalCredit
) {}
