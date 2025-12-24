package br.com.biosecure.model.client;

import java.util.ArrayList;
import br.com.biosecure.utils.InvalidAttributeException;
import br.com.biosecure.utils.ValidationException;

public class InvalidAddressException extends InvalidAttributeException {
    public InvalidAddressException(String invalidField, String message) {
        super(invalidField, message);

    }

    public InvalidAddressException(ArrayList<ValidationException> validationExceptions) {
        super(validationExceptions);
    }
}
