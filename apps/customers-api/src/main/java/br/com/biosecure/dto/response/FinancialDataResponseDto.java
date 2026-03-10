package br.com.biosecure.dto.response;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record FinancialDataResponseDto(

        @NotNull(message = "Share capital is required")
        BigDecimal shareCapital,

        @NotNull(message = "Total credit is required")
        BigDecimal totalCredit,

        @NotNull(message = "Utilized credit is required")
        BigDecimal utilizedCredit,

        @NotNull(message = "Customer ID is required")
        UUID customerId
) {}