package br.com.biosecure.domain.client;

public class InvalidAdressException extends IllegalArgumentException {
    public InvalidAdressException(String invalidField) {
        super(invalidField);
    }
}
