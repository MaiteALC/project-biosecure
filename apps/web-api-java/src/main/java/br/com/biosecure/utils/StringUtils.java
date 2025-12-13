package br.com.biosecure.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static String messageForNull = "The string is null.";
    private static String messageForEmpty = "The string is empty.";
    private static String messageForMaxLenght = "The string length is greater than allowed.";
    private static String messageForMinLenght = "The string length is less than allowed.";
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces). If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internaly by this method.
     */
    public static void validateString(String value, String propertyName, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }

        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than or less than the values passed as parameters. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param minLenght minimum length to be accepted <strong>(exclusive)</strong>.
     * @param maxLength maximum length to be accepted <strong>(exclusive)</strong>.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internaly by this method.
     */
    public static void validateString(String value, int minLength, String propertyName, int maxLength, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else if (value.length() < minLength) {
            notification.addError(propertyName, messageForMinLenght);
        }
        
        else if (value.length() > maxLength) {
            notification.addError(propertyName, messageForMaxLenght);
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than the value passed as parameter. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param maxLength maximum length to be accepted <strong>(exclusive)</strong>.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internaly by this method.
     */
    public static void validateString(String value, String propertyName, int maxLength, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else if (value.length() > maxLength) {
            notification.addError(propertyName, messageForMaxLenght);
        }
    }
    
    /**
     * Validates if the string is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, white spaces) or length is greater than the value passed as parameter. If yes, an exception will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param value A string that will be validated.
     * @param propertyName The name of property/attribute/field/variable that will be validated.
     * @param minLength minimum length to be accepted <strong>(exclusive)</strong>.
     * @param notification A {@code NotificationContext} object that stores the exceptions (if there is) added internaly by this method.
     */
    public static void validateString(String value, int minLength, String propertyName, NotificationContext notification) {
        if (value == null) {
            notification.addError(propertyName, messageForNull);
        }
        
        else if (value.isBlank()) {
            notification.addError(propertyName, messageForEmpty);
        }

        else if (value.length() < minLength) {
            notification.addError(propertyName, messageForMinLenght);
        }
    }

    /**
     * Validates if the email is {@code null} or contains <strong>only</strong> useless characters ({@code \t}, {@code \n}, withe spaces) or contains a invalid domain (common personal domains like: gmail.com or hotmail.com). If yes, an {@code ValidationException} will be added in the notification object with property name (parameter) and an informative message.
     * 
     * @param email A string that represents an email that will be validated.
     * @param notification A {@code NotificationContext} object that stores the {@code ValidationException} (if there is) added internaly by this method.
     */
    public static void validateCorporateEmail(String email, NotificationContext notification) {
        if (email == null) {
            notification.addError("email", messageForNull);
        }
        
        else if (email.isBlank()) {
            notification.addError("email", messageForEmpty);
        }

        else {
            final String REGEX = "^[A-Za-z0-9._%+-]+@(?!(gmail\\.com|hotmail\\.com|outlook\\.com|yahoo\\.com|live\\.com)$)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                notification.addError("email", "The email '" + email + "' have a not allowed domain.");
            }
        }
    }
}
