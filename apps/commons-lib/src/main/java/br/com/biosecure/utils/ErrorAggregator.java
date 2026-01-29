package br.com.biosecure.utils;

import java.util.List;
import java.util.Optional;

public class ErrorAggregator {
    public static Optional<ValidationException> verifyNull(Object object, String name) {
        if (object == null) {
            return Optional.of(new ValidationException(name, name + "mustn't be null"));
        }
        return Optional.empty();
    }

    public static void verifyNull(Object object, String name, NotificationContext notificationContext) {
        if (object == null) {
            notificationContext.addError(name, name + " mustn't be null");
        }
    }

    public static void aggregateValidationExceptions(List<Optional<ValidationException>> optionalExceptionsList, NotificationContext notification) {
        for (Optional<ValidationException> optional : optionalExceptionsList) {
            optional.ifPresent(exception -> notification.addError(exception.getInvalidProperty(), exception.getMessage()));
        }
    }
}
