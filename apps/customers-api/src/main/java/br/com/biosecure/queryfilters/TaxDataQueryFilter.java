package br.com.biosecure.queryfilters;

import br.com.biosecure.model.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

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
