package br.com.biosecure.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FinancialDataInputDto(

        @NotNull(message = "Share capital is required")
        BigDecimal shareCapital
) {}
