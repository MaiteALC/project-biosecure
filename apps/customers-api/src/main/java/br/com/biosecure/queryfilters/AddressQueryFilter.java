package br.com.biosecure.queryfilters;

import br.com.biosecure.model.Address;
import br.com.biosecure.specifications.AddressSpecs;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Data Transfer Object (DTO) designed to encapsulate dynamic filtering criteria
 * for the {@link Address} value object.
 * <p>
 * <strong>Architectural Role:</strong> This record acts as a binding target for web-layer
 * request parameters (usually query strings). It securely transports the requested
 * search criteria down to the persistence layer, where it is consumed by
 * {@link AddressSpecs} to generate database joins.
 *
 * @param state the exact state name to filter by
 * @param city the exact city name to filter by
 * @param neighborhood the exact neighborhood name to filter by
 * @param street the exact street name to filter by
 * @param number the exact address number to filter by
 * @param postalCode the exact postal code to filter by
 * @param deliveryAddress whether the address is a delivery address or not
 *
 * @since 1.0.0
 * @author MaiteALC
 */
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
