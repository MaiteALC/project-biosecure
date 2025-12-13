package br.com.biosecure.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class StringUtilsTest {
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   "})
    public void shouldRejectInvalidStrings(String invalidInput) {
        NotificationContext notification = new NotificationContext();
        
        StringUtils.validateString(invalidInput, "Test", notification);

        assertTrue(notification.hasErrors(), "Should have exception for input: " + invalidInput);
        assertEquals("Test", notification.getErrors().get(0).getInvalidProperty());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Camily", "A", "Valid String", "123"})
    public void shouldAcceptValidStrings(String validInput) {
        NotificationContext notification = new NotificationContext();
        
        StringUtils.validateString(validInput, "Test", notification);
        
        assertFalse(notification.hasErrors(), "Should NOT have exception for input: " + validInput);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Camily", "AB", "String", "123"})
    public void shouldAcceptValidStrings_WhenLenghtIsDelimited(String validInput) {
        NotificationContext notification = new NotificationContext();
        
        StringUtils.validateString(validInput, "Test (only max lenght)", 10, notification);
        
        assertFalse(notification.hasErrors(), "Should NOT have exception for input: " + validInput);
        
        StringUtils.validateString(validInput, 2, "Test (only max lenght)", notification);
        
        assertFalse(notification.hasErrors(), "Should NOT have exception for input: " + validInput);
        
        StringUtils.validateString(validInput, 1,  "Test (min and max length)", 6, notification);
        
        assertFalse(notification.hasErrors(), "Should NOT have exception for input: " + validInput);
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Ana Laura", "LMNOPQ", "String", "123456"})
    public void shouldRejectInvalidStrings_WhenLenghtIsDelimited(String invalidInput) {
        NotificationContext notification = new NotificationContext();
        NotificationContext notification2 = new NotificationContext();
        
        StringUtils.validateString(invalidInput, "Test (only max length)", 4, notification);
        
        assertTrue(notification.hasErrors(), "Should have exception for input: " + invalidInput);
        assertEquals("Test (only max length)", notification.getErrors().get(0).getInvalidProperty());
        
        StringUtils.validateString(invalidInput, 3, "Test (min and max length)", 5, notification2);
        
        assertTrue(notification2.hasErrors(), "Should have exception for input: " + invalidInput);
        assertEquals("Test (min and max length)", notification2.getErrors().get(0).getInvalidProperty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"test1@gmail.com", "test2@yahoo.com", "test3@hotmail.com", "test5@outlook.com", "test6@live.com", "not an email", "@p"} )
    public void shouldRejectInvalidEmails(String invalidEmail) {
        NotificationContext notification = new NotificationContext();

        StringUtils.validateCorporateEmail(invalidEmail, notification);

        assertTrue(notification.hasErrors(), "Should have exception for input: " + invalidEmail);
        assertEquals("email", notification.getErrors().get(0).getInvalidProperty());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"test1@oracle.com", "test2@biosecure.com.br", "test3@microsoft.net"} )
    public void shouldAcceptValidEmails(String validEmail) {
        NotificationContext notification = new NotificationContext();

        StringUtils.validateCorporateEmail(validEmail, notification);

        assertFalse(notification.hasErrors(), "Should NOT have exception for input: " + validEmail);
    }
}
