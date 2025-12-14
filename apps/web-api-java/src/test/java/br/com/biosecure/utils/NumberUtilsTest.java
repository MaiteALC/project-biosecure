package br.com.biosecure.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class NumberUtilsTest {
    
    @Test
    public void shouldValidedCorrectly() {
        NotificationContext notificationContext = new NotificationContext();

        NumberUtils.validateNumericalAttribute(20, 2, "test", 25, notificationContext);

        NumberUtils.validateNumericalAttribute(BigDecimal.valueOf(5000000000L), BigDecimal.valueOf(1000000000L), "test", BigDecimal.valueOf(10000000000L), notificationContext);

        assertFalse(notificationContext.hasErrors());
    }

    @Test
    public void shouldAddErrorsCorrectly() {
        NotificationContext notificationContext = new NotificationContext();

        NumberUtils.validateNumericalAttribute(0.2, 1.3, "test", 2.5, notificationContext);
        
        NumberUtils.validateNumericalAttribute(11.7, 1.3, "test", 2.5, notificationContext);

        NumberUtils.validateNumericalAttribute(BigDecimal.valueOf(30000L), BigDecimal.valueOf(100000L), "test", BigDecimal.valueOf(2000000L), notificationContext);

        assertTrue(notificationContext.hasErrors());
        
        ArrayList<ValidationException> errors = notificationContext.getErrors();
        
        assertEquals("The number is less than allowed", errors.get(0).getMessage());
        assertEquals("The number is greater than allowed", errors.get(1).getMessage());
        assertEquals("The number is less than allowed", errors.get(2).getMessage());
    }

    @Test
    public void shouldThrowException_WhenSomeAttributeIsNull_InValidateNumericalAttribute() {
        String expectedMsg = "Min/max value, property name and notification object mustn't be null! Verify these parameters.";

        NumberUtils.validateNumericalAttribute(null, BigDecimal.valueOf(10000000L), "test", BigDecimal.valueOf(2000000L), new NotificationContext());

        assertThrows(NullPointerException.class, () -> {
            NumberUtils.validateNumericalAttribute(BigDecimal.TEN, null, "test", 200, new NotificationContext());
        });
        
        assertThrows(NullPointerException.class, () -> {
            NumberUtils.validateNumericalAttribute(BigDecimal.valueOf(1000.2), Integer.parseInt("50"), null, Integer.parseInt("500"), new NotificationContext());
        });
        
        assertThrows(NullPointerException.class, () -> {
            NumberUtils.validateNumericalAttribute(BigDecimal.valueOf(100.2), Integer.parseInt("50"), "test", null, new NotificationContext());
        });
        
        Exception nullException = assertThrows(NullPointerException.class, () -> {
            NumberUtils.validateNumericalAttribute(BigDecimal.TWO, Integer.parseInt("50"), "test", 300, null);
        });

        assertEquals(expectedMsg, nullException.getMessage());
    }

    @Test
    public void shouldThrowException_WhenSomeAttributeIsNull_InValidateExpirationDate() {
        String expectedMsgForNull = "Property name and notification object mustn't be null. Verify these parameters.";

        Exception nullException = assertThrows(NullPointerException.class, () -> {
            NumberUtils.validateExpirationDate(LocalDate.of(2027, 3, 3),null, null);
        });
        
        assertEquals(expectedMsgForNull, nullException.getMessage());
        
        NotificationContext notf =  new NotificationContext();

        NumberUtils.validateExpirationDate(null, "date", notf);

        assertTrue(notf.hasErrors());
        assertEquals("The date is null.", notf.getErrors().get(0).getMessage());
    }
}
