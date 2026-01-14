package br.com.biosecure.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String messageForNull = "The string is null.";
    private static final String messageForEmpty = "The string is empty.";
    private static final String messageForMaxLength = "The string length is greater than allowed.";
    private static final String messageForMinLength = "The string length is less than allowed.";
    private static final String messageForDigits = "The string mustn't contain digits.";
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces). If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internally by this method.
     */
    public static void validateString(String value, String propertyName, boolean acceptDigits, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }

        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else if (!acceptDigits && value.matches("[0-9]+")) {
            notification.addError(propertyName, messageForDigits);
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than or less than the values passed as parameters. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param minLength minimum length to be accepted <strong>(exclusive)</strong>.
     * @param maxLength maximum length to be accepted <strong>(exclusive)</strong>.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internally by this method.
     */
    public static void validateString(String value, int minLength, String propertyName, int maxLength, boolean acceptDigits, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else {
            if (!acceptDigits && value.matches("[0-9]+")) {
                notification.addError(propertyName, messageForDigits);
            }

            if (value.length() < minLength) {
                notification.addError(propertyName, messageForMinLength);
            }

            else if (value.length() > maxLength) {
                notification.addError(propertyName, messageForMaxLength);
            }
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than the value passed as parameter. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param maxLength maximum length to be accepted <strong>(exclusive)</strong>.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internally by this method.
     */
    public static void validateString(String value, String propertyName, int maxLength, boolean acceptDigits, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else {
            if (!acceptDigits && value.matches("[0-9]+")) {
                notification.addError(propertyName, messageForDigits);
            }

            if (value.length() > maxLength) {
                notification.addError(propertyName, messageForMaxLength);
            }
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than the value passed as parameter. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param minLength minimum length to be accepted <strong>(exclusive)</strong>.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internally by this method.
     */
    public static void validateString(String value, int minLength, String propertyName, boolean acceptDigits, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else {
            if (!acceptDigits && value.matches("[0-9]+")) {
                notification.addError(propertyName, messageForDigits);
            }

            if (value.length() < minLength) {
                notification.addError(propertyName, messageForMinLength);
            }
        }
    }

    /**
     * Validates if the email is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, withe spaces) or contains an invalid domain (common personal domains like: gmail.com or hotmail.com). If yes, an {@code ValidationException} will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param email A string that represents an email that will be validated.
     * @param notification A {@code NotificationContext} object that stores the {@code ValidationException} (if there is) added internally by this method.
     */
    public static void validateCorporateEmail(String email, NotificationContext notification) {
        if (email == null) {
            notification.addError("email", messageForNull);
        }
        
        else if (email.isBlank()) {
            notification.addError("email", messageForEmpty);
        }

        else {
            final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@(?!(gmail\\.com|hotmail\\.com|outlook\\.com|yahoo\\.com|live\\.com)$)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                notification.addError("email", "The email '" + email + "' has a not allowed domain.");
            }
        }
    }
}
