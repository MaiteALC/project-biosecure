package br.com.biosecure.model;

import br.com.biosecure.utils.InvalidAttributeException;
import br.com.biosecure.utils.ValidationException;

import java.util.ArrayList;

/**
 * Thrown to indicate that a provided attribute violates the business rules
 * or structural constraints of a {@link FinancialData} entity.
 * <p>
 * This is a domain-level exception typically triggered during the instantiation
 * or mutation of an entity when invalid data is detected, preventing the object
 * from entering an inconsistent state.
 * <p>
 * <strong>API Handling:</strong> Within the web layer, this exception should be
 * intercepted and mapped to an HTTP 400 (Bad Request) or HTTP 422 (Unprocessable Entity),
 * providing the client with the specific validation failure details.
 *
 * @see InvalidAttributeException
 * @see FinancialData
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class InvalidFinancialDataException extends InvalidAttributeException {
    public InvalidFinancialDataException(ArrayList<ValidationException> errors) {
        super(errors);
    }

    public InvalidFinancialDataException(String attributeName, String message) {
        super(attributeName, message);
    }
}
