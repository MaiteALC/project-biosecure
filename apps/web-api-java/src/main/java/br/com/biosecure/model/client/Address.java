package br.com.biosecure.model.client;

import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;
import br.com.biosecure.utils.NotificationContext;

public class Address {
    private final String state;
    private final String city;
    private final String neighborhood;
    private final String street;
    private final String postalCode;
    private final int number;

    public Address(String state, String city, String neighborhood, String street, int number, String postalCode) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(number, 1, "number", 99999, notification);

        StringUtils.validateString(state, 2, "state name", notification);
        StringUtils.validateString(city, 2, "city name", notification);
        StringUtils.validateString(neighborhood, 2, "neighborhood name", notification);
        StringUtils.validateString(street, 2, "street name", notification);

        StringUtils.validateString(postalCode.replace("-", ""), 8, "postal code", 8, notification);

        if (notification.hasErrors()) {
            throw new InvalidAddressException(notification.getErrors());
        }

        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Address [state: " + state + ", city: " + city + ", neighborhood: " + neighborhood + ", street: " + street + ", number: " + number + ", postalCode: " + postalCode + "]";
    }
}