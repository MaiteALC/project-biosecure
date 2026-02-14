package br.com.biosecure.dto;

import br.com.biosecure.model.Cnpj;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FinancialDataInputDto(

        @NotNull(message = "CNPJ is required")
        Cnpj cnpj,

        @NotNull(message = "Share capital is required")
        BigDecimal shareCapital
) {}
