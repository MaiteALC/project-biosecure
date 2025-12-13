package br.com.biosecure.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class NotificationContextTest {

    @Test
    void shouldStoreErrorsCorrectly() {
        NotificationContext notification = new NotificationContext();
        
        notification.addError("name", "invalid name");
        
        assertTrue(notification.hasErrors());

        assertEquals(1, notification.getErrors().size());
        assertEquals("name", notification.getErrors().get(0).getInvalidProperty());
        assertEquals("invalid name", notification.getErrors().get(0).getMessage());
    }

    @Test
    void shouldStartWithoutErrors() {
        NotificationContext notification = new NotificationContext();

        assertFalse(notification.hasErrors());
        assertNotNull(notification.getErrors());
    }
}