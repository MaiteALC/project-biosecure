package br.com.biosecure.model.product;

public class InvalidProductAttributeException extends IllegalArgumentException {
    private final String invalidAttribute;

    public InvalidProductAttributeException(String attribute) {
        super("The field '" + attribute + "' mustn't be empty");
        this.invalidAttribute = attribute;
    }

    public String getInvalidAttribute() {
        return invalidAttribute;
    }
}
