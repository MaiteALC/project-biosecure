package br.com.biosecure.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorAggregatorTest {

    @Test
    public void shouldVerifyNullsCorrectly() {
        Optional<ValidationException> exception = ErrorAggregator.verifyNull(null, "test");

        assertTrue(exception.isPresent());

        Optional<ValidationException> exception2 = ErrorAggregator.verifyNull(null, null);

        assertTrue(exception2.isPresent());
        assertNull(exception2.get().getInvalidProperty());
    }

    @Test
    public void shouldProcessNullsCorrectly() {
        NotificationContext notificationContext = new NotificationContext();

        ErrorAggregator.aggregateValidationExceptions(
                List.of(
                        ErrorAggregator.verifyNull(null, "test1"),
                        ErrorAggregator.verifyNull(null, "test2"),
                        ErrorAggregator.verifyNull(null, "test3")
                ),
                notificationContext
        );

        assertTrue(notificationContext.hasErrors());

        assertEquals(3,  notificationContext.getErrors().size());
    }
}
