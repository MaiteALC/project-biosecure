package br.com.biosecure.queryfilters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

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
