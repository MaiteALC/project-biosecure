package br.com.biosecure.queryfilters;

import br.com.biosecure.model.FinancialData;
import br.com.biosecure.specifications.FinancialDataSpecs;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) designed to encapsulate dynamic filtering criteria
 * for the {@link FinancialData} entity.
 * <p>
 * <strong>Architectural Role:</strong> This record acts as a binding target for web-layer
 * request parameters (usually query strings). It securely transports the requested
 * search criteria down to the persistence layer, where it is consumed by
 * {@link FinancialDataSpecs} to generate database queries.
 *
 * @param customerId the exact customer ID to search for
 * @param shareCapital the exact share capital value to search for
 * @param shareCapitalLowerLimit the inclusive lower bound for the share capital value
 * @param shareCapitalUpperLimit the inclusive upper bound for the share capital value
 * @param totalCredit the exact total credit value to search for
 * @param totalCreditLowerLimit the inclusive lower bound for the share capital value
 * @param totalCreditUpperLimit the inclusive upper bound for the share capital value
 * @param utilizedCredit the exact utilized credit value to search for
 * @param utilizedCreditLowerLimit the inclusive lower bound for the utilized credit value
 * @param utilizedCreditUpperLimit the inclusive upper bound for the utilized credit value
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record FinancialDataQueryFilter (

        @JsonProperty(value = "customer_id")
        UUID customerId,

        @JsonProperty(value = "share_capital")
        BigDecimal shareCapital,

        @JsonProperty(value = "share_capital_lower_limit")
        BigDecimal shareCapitalLowerLimit,

        @JsonProperty(value = "share_capital_upper_limit")
        BigDecimal shareCapitalUpperLimit,

        @JsonProperty(value = "total_credit")
        BigDecimal totalCredit,

        @JsonProperty(value = "total_credit_lower_limit")
        BigDecimal totalCreditLowerLimit,

        @JsonProperty(value = "total_credit_upper_limit")
        BigDecimal totalCreditUpperLimit,

        @JsonProperty(value = "utilized_credit")
        BigDecimal utilizedCredit,

        @JsonProperty(value = "utilized_credit_lower_limit")
        BigDecimal utilizedCreditLowerLimit,

        @JsonProperty(value = "utilized_credit_upper_limit")
        BigDecimal utilizedCreditUpperLimit
) {}
