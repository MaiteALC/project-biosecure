package br.com.biosecure.model;

import br.com.biosecure.utils.InvalidAttributeException;
import br.com.biosecure.utils.ValidationException;

import java.util.ArrayList;

public class InvalidFinancialDataException extends InvalidAttributeException {
    public InvalidFinancialDataException(ArrayList<ValidationException> errors) {
        super(errors);
    }

    public InvalidFinancialDataException(String attributeName, String message) {
        super(attributeName, message);
    }
}
