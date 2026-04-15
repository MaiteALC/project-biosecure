package br.com.biosecure.dto.response;

import br.com.biosecure.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * {@code GET} operations. It projects the {@link Address} value object state
 * into a format optimized for consumption.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param state the registered state name (abbreviated with 2 letters)
 * @param city the registered city name
 * @param neighborhood the registered neighborhood name
 * @param street the registered street name
 * @param number the registered address number (may contain the complement, e.g. 123A, 123B)
 * @param postalCode the registered 8-digit Brazilian postal code
 * @param deliveryAddress whether the address is a delivery address or not
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record AddressResponseDto(
        @NotBlank(message = "State name is required")
        String state,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Neighborhood name is required")
        String neighborhood,

        @NotBlank(message = "Street name is required")
        String street,

        @NotBlank(message = "Address number is required")
        String number,

        @NotBlank(message = "Postal code is required")
        @JsonProperty("postal_code")
        String postalCode,

        @JsonProperty("is_delivery_address")
        boolean deliveryAddress
) {}
