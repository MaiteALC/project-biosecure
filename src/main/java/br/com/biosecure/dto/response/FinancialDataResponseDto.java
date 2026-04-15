package br.com.biosecure.dto.response;

import br.com.biosecure.model.FinancialData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * {@code GET} operations. It projects the {@link FinancialData} entity state
 * into a format optimized for consumption.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param shareCapital the share capital amount in BRL
 * @param totalCredit the total credit amount in BRL
 * @param utilizedCredit the utilized credit amount in BRL
 * @param customerId the unique identifier for this customer, used for subsequent API calls
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record FinancialDataResponseDto(

        @NotNull(message = "Share capital is required")
        @JsonProperty("share_capital")
        BigDecimal shareCapital,

        @NotNull(message = "Total credit is required")
        @JsonProperty("total_credit")
        BigDecimal totalCredit,

        @NotNull(message = "Utilized credit is required")
        @JsonProperty("utilized_credit")
        BigDecimal utilizedCredit,

        @NotNull(message = "Customer ID is required")
        @JsonProperty("customer_id")
        UUID customerId
) {}