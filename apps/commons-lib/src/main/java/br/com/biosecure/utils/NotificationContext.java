package br.com.biosecure.utils;

import java.util.ArrayList;

/**
 * A contextual container designed to aggregate validation errors, serving as
 * the primary implementation of the <strong>Notification Pattern</strong>.
 * <p>
 * Unlike a traditional fail-fast approach where the first encountered error
 * halts the execution immediately, this class allows multiple validation steps
 * to proceed independently, accumulating any resulting {@link ValidationException}s
 * into a single instance.
 * <p>
 * <strong>Usage Workflow:</strong> After the validation phase is complete, the caller
 * should inspect this context. If errors were accumulated, the caller can then extract
 * the collected messages to construct and throw a single, comprehensive exception
 * containing all identified violations.
 *
 * @see ValidationException
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class NotificationContext {
    ArrayList<ValidationException> exceptions = new ArrayList<>();

    public NotificationContext() {}

    public void addError(String propertyName, String message) {
        this.exceptions.add(new ValidationException(propertyName, message));
    }

    public boolean hasErrors() {
        return !exceptions.isEmpty();
    }

    public ArrayList<ValidationException> getErrors() {
        return this.exceptions;
    }
}   
