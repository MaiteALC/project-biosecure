package br.com.biosecure.domain.client;

import br.com.biosecure.domain.client.InvalidAdressException;

public class Adress {
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private int number;
    private String postalCode;

    public Adress(String state, String city, String neighborhood, String street, int number, String postalCode) {
        if (postalCode.replace("-", "").length() != 8) {
            throw new InvalidAdressException("postal code");
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

    public int getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }
}