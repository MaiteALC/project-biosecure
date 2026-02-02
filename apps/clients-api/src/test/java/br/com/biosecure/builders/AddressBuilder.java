package br.com.biosecure.builders;

import br.com.biosecure.model.Address;

public class AddressBuilder {
    private String state = "MG";
    private String city = "BH";
    private String neighborhood = "random neighborhood name";
    private String street = "random street name";
    private int number = 549;
    private String postalCode = "12345-067";
    private boolean deliveryAddress = true;

    public AddressBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public AddressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder withNumber(int number) {
        this.number = number;
        return this;
    }

    public AddressBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder withDeliveryAddress(boolean deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public Address build() {
        return new Address(state, city, neighborhood, street, number, postalCode, deliveryAddress);
    }
}
