package br.com.biosecure.domain.client;

public class InvalidCnpjException extends IllegalArgumentException {
    public InvalidCnpjException(String msg) {
        super(msg);
    }
}
