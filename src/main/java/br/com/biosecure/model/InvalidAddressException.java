package br.com.biosecure.model;

import br.com.biosecure.utils.InvalidAttributeException;
import br.com.biosecure.utils.ValidationException;

import java.util.ArrayList;

/**
 * Thrown to indicate that a provided attribute violates the business rules
 * or structural constraints of a {@link Address} entity.
 * <p>
 * This is a domain-level exception typically triggered during the instantiation
 * of the value object (e.g., via a Builder) when invalid data
 * is detected, preventing the object from entering an inconsistent state.
 * <p>
 * <strong>API Handling:</strong> Within the web layer, this exception should be
 * intercepted and mapped to an HTTP 400 (Bad Request) or HTTP 422 (Unprocessable Entity),
 * providing the client with the specific validation failure details.
 *
 * @see InvalidAttributeException
 * @see Address
 * @see Address.AddressBuilder
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class InvalidAddressException extends InvalidAttributeException {
    public InvalidAddressException(String invalidField, String message) {
        super(invalidField, message);

    }

    public InvalidAddressException(ArrayList<ValidationException> validationExceptions) {
        super(validationExceptions);
    }
}
