package br.com.biosecure.queryfilters;

import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.RegistrationStatus;
import br.com.biosecure.model.TaxData;
import br.com.biosecure.specifications.TaxDataSpecs;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * A Data Transfer Object (DTO) designed to encapsulate dynamic filtering criteria
 * for the {@link TaxData} value object.
 * <p>
 * <strong>Architectural Role:</strong> This record acts as a binding target for web-layer
 * request parameters (usually query strings). It securely transports the requested
 * search criteria down to the persistence layer, where it is consumed by
 * {@link TaxDataSpecs} to generate database joins.
 *
 * @param lastSearchDate the exact last search date to search for
 * @param lastSearchDateAfter the inclusive lower bound for the last search period
 * @param lastSearchDateBefore the inclusive upper bound for the last search period
 * @param activitiesStartDate the exact customer activities start date to search for
 * @param activitiesStartDateAfter the inclusive lower bound for the customer activities start date
 * @param activitiesStartDateBefore the inclusive upper bound for the customer activities start date
 * @param registrationStatus the exact registration status to search for
 * @param statusDescription the exact or partial status description to search for
 * @param cnae the exact formatted {@link Cnae} number to search for
 * @param cnaeDescription the exact CNAE description to search for
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record TaxDataQueryFilter (

        @JsonProperty(value = "last_search_date")
        LocalDate lastSearchDate,

        @JsonProperty(value = "last_search_date_after_than")
        LocalDate lastSearchDateAfter,

        @JsonProperty(value = "last_search_date_before_than")
        LocalDate lastSearchDateBefore,

        @JsonProperty(value = "activities_start_date")
        LocalDate activitiesStartDate,

        @JsonProperty(value = "activities_start_date_after_than")
        LocalDate activitiesStartDateAfter,

        @JsonProperty(value = "activities_start_date_before_than")
        LocalDate activitiesStartDateBefore,

        @JsonProperty(value = "registration_status")
        RegistrationStatus registrationStatus,

        @JsonProperty(value = "registration_status_description")
        String statusDescription,

        @JsonProperty(value = "cnae_code")
        String cnae,

        @JsonProperty(value = "cnae_description")
        String cnaeDescription
) {}
