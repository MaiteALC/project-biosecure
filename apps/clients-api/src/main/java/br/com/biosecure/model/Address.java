package br.com.biosecure.model;

import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;
import br.com.biosecure.utils.NotificationContext;

public record Address(String state, String city, String neighborhood, String street, int number, String postalCode) {
    public Address {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(number, 1, "number", 99999, notification);

        StringUtils.validateString(state, MIN_LENGTH, "state name", MAX_LENGTH, false, notification);
        StringUtils.validateString(city, MIN_LENGTH, "city name", MAX_LENGTH, false, notification);
        StringUtils.validateString(neighborhood, MIN_LENGTH, "neighborhood name", MAX_LENGTH, true, notification);
        StringUtils.validateString(street, MIN_LENGTH, "street name", MAX_LENGTH, true, notification);

        if (notification.hasErrors()) {
            throw new InvalidAddressException(notification.getErrors());
        }

    }

    @Override
    public String toString() {
        return "Address [state: " + state + ", city: " + city + ", neighborhood: " + neighborhood + ", street: " + street + ", number: " + number + ", postalCode: " + postalCode + "]";
    }
}