package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Address {

    @Column(name = "state_name", nullable = false)
    String state;

    @Column(name = "city_name", nullable = false)
    String city;

    @Column(name = "neighborhood_name", nullable = false)
    String neighborhood;

    @Column(name = "street_name", nullable = false)
    String street;

    @Column(name = "address_number", nullable = false, length = 7)
    String number;

    @Column(name = "postal_code", nullable = false, length = 9)
    String postalCode;

    boolean deliveryAddress;

    private static final Pattern POSTAL_CODE_REGEX = Pattern.compile("^[0-9]{5}-?[0-9]{3}$");

    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class AddressBuilder {
        private String state;
        private String city;
        private String neighborhood;
        private String street;
        private String number;
        private String postalCode;
        private boolean deliveryAddress;

        public Address build() {
            NotificationContext addressNotification = new NotificationContext();

            if (!isValidPostalCode(postalCode)) {
                addressNotification.addError("postal code", "postal code is null or in invalid format");
            }

            StringUtils.validateString(number, "number", true, addressNotification);

            StringUtils.validateString(state, 2, "state name", 96, false, addressNotification);

            StringUtils.validateString(city, 2, "city name", 96, false, addressNotification);

            StringUtils.validateString(neighborhood, 2, "neighborhood name", 96, true, addressNotification);

            StringUtils.validateString(street, 2, "street name", 96, true, addressNotification);

            if (addressNotification.hasErrors()) {
                throw new InvalidAddressException(addressNotification.getErrors());
            }

            return new Address(state, city, neighborhood, street, number, postalCode, deliveryAddress);
        }
    }

    public static boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_REGEX.matcher(postalCode).matches();
    }

    @Override
    public String toString() {
        return new StringBuilder("Address = ")
                .append("[state=").append(state)
                .append(", city=").append(city)
                .append(", neighborhood=").append(neighborhood)
                .append(", street=").append(street)
                .append(", number=").append(number)
                .append(", postalCode=").append(postalCode)
                .append(']').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Address other = (Address) obj;

        return this.postalCode.equals(other.postalCode) &&
                this.street.equals(other.street) &&
                this.number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, city, neighborhood, street, number, postalCode, deliveryAddress);
    }
}