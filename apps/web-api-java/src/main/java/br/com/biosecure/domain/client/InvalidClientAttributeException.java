package br.com.biosecure.domain.client;

public class InvalidClientAttributeException extends IllegalArgumentException {
    private final String invalidAttribute;

    public InvalidClientAttributeException(String attribute) {
        super("The field '" + attribute + "' musn't be empty");
        this.invalidAttribute = attribute;
    }

    public String getInvalidAttribute() {
        return invalidAttribute;
    }
}
