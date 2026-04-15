package br.com.biosecure.dto.input;

import br.com.biosecure.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * A Data Transfer Object (DTO) designed to encapsulate input data from API request payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the primary contract for
 * {@code POST} requests. It securely transports raw input data from the web layer to the
 * service layer, where it is used to instantiate the {@link Address} value object.
 * <p>
 * <strong>Validation:</strong> It enforces strict input constraints using standard
 * annotations, acting as the first line of defense by rejecting malformed or missing
 * mandatory fields before they reach the domain logic.
 *
 * @param state the abbreviated (2 letters) state name; must not be blank
 * @param city the full city name; must not be blank
 * @param neighborhood the full neighborhood name; must not be blank
 * @param street the full street name; must not be blank
 * @param number the address number; can contain the complement (e.g. 123A, 123B), must not be blank
 * @param postalCode the plain or formatted 8-digit Brazilian postal code; must not be blank
 * @param deliveryAddress whether the address is a delivery address or not
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record AddressInputDto(
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
