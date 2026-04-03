package br.com.biosecure.utils;

import java.util.ArrayList;

/**
 * A base exception class thrown to indicate that one or more attributes
 * violate the business rules or structural constraints of a domain entity.
 * <p>
 * Designed to be extended by domain-specific exceptions, this class provides
 * an agnostic and standardized mechanism to aggregate and format error messages
 * when multiple attribute validations fail simultaneously.
 * <p>
 * <strong>Architectural Note:</strong> Catching this base class allows global
 * exception handlers (such as a {@code @RestControllerAdvice}) to process and
 * translate any attribute validation failure across the application uniformly.
 *
 * @see ValidationException
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class InvalidAttributeException extends RuntimeException {
    protected String invalidAttribute;
    protected ArrayList<String> invalidAttributesArray = new ArrayList<>();

    protected InvalidAttributeException(String attributeName, String message) {
        super(message);

        this.invalidAttribute = attributeName;
    }

    protected InvalidAttributeException(ArrayList<ValidationException> exceptionsArray) {
        super(createCustomMessage(exceptionsArray));
        
        for (ValidationException exception : exceptionsArray) {
            this.invalidAttributesArray.add(exception.getInvalidProperty());   
        }
    }

    /**
     * Retrieves the invalid attribute(s) as a single formatted {@link String}.
     * <p>
     * If the underlying data was originally captured as a collection of attributes,
     * this method dynamically concatenates them into a single string representation.
     * <p>
     * <strong>Note:</strong> This method may return {@code null} if no attributes
     * were explicitly provided during the exception instantiation.
     *
     * @return the invalid attribute name(s) as a {@code String}, or {@code null}
     */
    public String getInvalidAttribute() {
        if (invalidAttribute == null) {
            if (invalidAttributesArray.size() == 1) {
                return invalidAttributesArray.toString().replace("[", "").replace("]", "");
            }

            else {
                return invalidAttributesArray.toString();
            }
        }
        
        return  invalidAttribute;
    }

    /**
     * Retrieves the invalid attribute(s) as a strongly typed {@link ArrayList}.
     * <p>
     * If the underlying data was originally captured as a single {@link String},
     * this method dynamically wraps it into a list. This guarantees a consistent,
     * collection-based interface for the caller regardless of the internal state.
     * <p>
     * <strong>Note:</strong> This method is null-safe. It may return an empty
     * list, but it will never return {@code null}.
     *
     * @return a non-null {@code ArrayList} containing the invalid attribute name(s)
     */
    public ArrayList<String> getInvalidAttributesArray() {
        if (invalidAttribute != null) {
            this.invalidAttributesArray.add(invalidAttribute);
        }

        return invalidAttributesArray;
    }

    private static String createCustomMessage(ArrayList<ValidationException> errors) {
        StringBuilder message = new StringBuilder("These attributes are invalids:\n");

        for (ValidationException currentError : errors) {
            message.append("\t - ");
            message.append(currentError.getInvalidProperty());

            message.append(" | "); 
            
            message.append(currentError.getMessage());
            message.append('\n');
        }

        return message.toString();
    }
}
