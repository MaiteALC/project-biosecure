package br.com.biosecure.dto;

import jakarta.validation.constraints.NotBlank;

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
        String postalCode,

        boolean deliveryAddress
) {}
