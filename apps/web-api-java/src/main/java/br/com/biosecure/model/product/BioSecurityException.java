package br.com.biosecure.model.product;

import java.util.ArrayList;

public class BioSecurityException extends RuntimeException {
    private final ArrayList<String> invalidAttributes;

    public BioSecurityException(String message, ArrayList<String> invalidAttributes) {
        super("SECURITY WARNING: " + message);

        this.invalidAttributes = invalidAttributes;
    }

    public ArrayList<String> getInvalidAttributes() {
        return invalidAttributes;
    }
}
