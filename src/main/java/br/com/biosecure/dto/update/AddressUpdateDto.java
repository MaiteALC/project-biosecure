package br.com.biosecure.dto.update;

public record AddressUpdateDto(
        String state,

        String city,

        String neighborhood,

        String street,

        String number,

        String postalCode,

        Boolean deliveryAddress
) {}
