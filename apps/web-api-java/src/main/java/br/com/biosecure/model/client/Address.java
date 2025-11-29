package br.com.biosecure.model.client;

public record Address(String state, String city, String neighborhood, String street, int number, String postalCode) {
    public Address {
        if (postalCode.replace("-", "").length() != 8 || postalCode.isBlank()) {
            throw new InvalidAddressException("postal code");
        }

        if (number < 1 || number > 99999) {
            throw new InvalidAddressException("number");
        }
    }
}