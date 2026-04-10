package br.com.biosecure.queryfilters;

import br.com.biosecure.model.*;
import br.com.biosecure.specifications.CustomerSpecs;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) designed to encapsulate dynamic filtering criteria
 * for the {@link Customer} aggregate root.
 * <p>
 * <strong>Architectural Role:</strong> This record acts as a binding target for web-layer
 * request parameters (usually query strings). It securely transports the requested
 * search criteria down to the persistence layer, where it is consumed by
 * {@link CustomerSpecs} to generate database queries.
 *
 * @see FinancialDataQueryFilter
 * @see AddressQueryFilter
 * @see TaxDataQueryFilter
 *
 * @param corporateName the partial or exact corporate name to filter by
 * @param id the exact ID to search for
 * @param cnpj the exact formatted {@link Cnpj} number to search for
 * @param email the exact email contact to search for
 * @param addressFilter the nested criteria to filter customers based on their {@link Address}
 * @param financialDataFilter the nested criteria to filter customers based on their {@link FinancialData}
 * @param taxDataFilter the nested criteria to filter customers based on their {@link TaxData}
 * @param registrationDate the exact registration date to filter by
 * @param registrationDateAfter the inclusive lower bound for the registration period
 * @param registrationDateBefore the inclusive upper bound for the registration period
 *
 * @since 1.0.0
 * @author MaiteALC
 */
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
