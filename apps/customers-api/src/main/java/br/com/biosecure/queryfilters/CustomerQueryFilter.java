package br.com.biosecure.queryfilters;

import br.com.biosecure.model.Cnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerQueryFilter (

        @JsonProperty(value = "corporate_name")
        String corporateName,

        @JsonProperty(value = "customer_id")
        UUID id,

        @JsonProperty(value = "cnpj_number")
        Cnpj cnpj,

        @JsonProperty(value = "corporate_email")
        String email,

        @JsonProperty(value = "address_filter")
        AddressQueryFilter addressFilter,

        @JsonProperty(value = "financial_data_filters")
        FinancialDataQueryFilter financialDataFilter,

        @JsonProperty(value = "tax_data_filters")
        TaxDataQueryFilter taxDataFilter,

        @JsonProperty(value = "registration_date")
        LocalDate registrationDate,

        @JsonProperty(value = "registration_date_after_than")
        LocalDate registrationDateAfter,

        @JsonProperty(value = "registration_date_before_than")
        LocalDate registrationDateBefore
) {}
