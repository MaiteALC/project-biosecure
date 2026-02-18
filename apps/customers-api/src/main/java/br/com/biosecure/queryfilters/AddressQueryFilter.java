package br.com.biosecure.queryfilters;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressQueryFilter (

        String state,

        String city,

        String neighborhood,

        String street,

        String number,

        @JsonProperty(value = "postal_code")
        String postalCode,

        @JsonProperty(value = "is_delivery_address")
        Boolean deliveryAddress
) {}
