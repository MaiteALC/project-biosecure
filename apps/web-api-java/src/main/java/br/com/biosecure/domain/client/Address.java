package br.com.biosecure.domain.client;

public record Address(String state, String city, String neighborhood, String street, int number, String postalCode) {
    public Address {
        if (postalCode.replace("-", "").length() != 8 || postalCode.isBlank()) {
            throw new InvalidAddressException("postal code");
        }

        if (1 < number) {
            throw new InvalidAddressException("number");
        }
    }
}