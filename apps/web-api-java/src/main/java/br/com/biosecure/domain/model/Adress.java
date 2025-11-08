package br.com.biosecure.domain.model;

public record Adress(String state, String city, String neighborhood, String street, String postalCode, String num) {}
// TODO make a postal code validation