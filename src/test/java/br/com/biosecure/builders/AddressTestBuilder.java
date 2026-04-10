package br.com.biosecure.builders;

import br.com.biosecure.model.Address;

public class AddressTestBuilder {
    private String state = "MG";
    private String city = "BH";
    private String neighborhood = "random neighborhood name";
    private String street = "random street name";
    private String number = "549A";
    private String postalCode = "12345-067";
    private boolean deliveryAddress = true;

    public AddressTestBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public AddressTestBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AddressTestBuilder withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public AddressTestBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressTestBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public AddressTestBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressTestBuilder withDeliveryAddress(boolean deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public static AddressTestBuilder anAddress() {
        return new AddressTestBuilder();
    }

    public Address build() {
        return Address.builder()
                .state(state)
                .city(city)
                .neighborhood(neighborhood)
                .street(street)
                .number(number)
                .postalCode(postalCode)
                .deliveryAddress(deliveryAddress)
                .build();
    }
}
