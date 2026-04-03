package br.com.biosecure.utils;

/**
 * A foundational exception representing a business rule violation.
 * <p>
 * Unlike traditional fail-fast exceptions that halt application execution immediately,
 * this class is specifically designed to support the <strong>Notification Pattern</strong>.
 * It serves as a standardized data carrier to be collected and managed by error
 * aggregation contexts, such as {@link NotificationContext} and {@link ErrorAggregator}.
 * <p>
 * <strong>Usage Note:</strong> The primary objective of this class is not to be
 * thrown standalone. Instead, instances of this class should be instantiated and
 * accumulated within an aggregator, allowing multiple validation failures to be
 * processed and reported as a single batch.
 *
 * @see NotificationContext
 * @see ErrorAggregator
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class ValidationException extends RuntimeException {
    private final String invalidProperty;
    
    public ValidationException(String invalidPropertyName, String message) {
        super(message);
        
        this.invalidProperty = invalidPropertyName;
    }
    
    public String getInvalidProperty() {
        return invalidProperty;
    }
}