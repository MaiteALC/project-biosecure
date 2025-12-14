package br.com.biosecure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class NumberUtils {
    private final static String MESSAGE_FOR_NULL = "The number is null";
    private final static String MESSAGE_FOR_MAX_VALUE = "The number is greater than allowed";
    private final static String MESSAGE_FOR_MIN_VALUE = "The number is less than allowed";

    /**
     * Validates a number, verifying if it is null or is out of min/max limits. If yes, an error will be added in the notification with a informative message and respective property name.
     * 
     * @param <T> a type wich extends {@code Number}
     * @param number the value that will be validated (can be null)
     * @param minValue minimum value to be accepted <strong>(exclusive)</strong>
     * @param propertyName name that will be included in notification errors (if there is)
     * @param maxValue maximum value to be accepted <strong>(exclusive)</strong>
     * @param notification notification object to store and get the errors (if there is)
     */
    public static <T extends Number> void validateNumericalAttribute(T number, T minValue, String propertyName, T maxValue, NotificationContext notification) {
        if (minValue == null || propertyName == null || maxValue == null || notification == null) {
            throw new NullPointerException(
                "Min/max value, property name and notification object mustn't be null! Verify these parameters."
            );
        }

        if (number == null) {
            notification.addError(propertyName, MESSAGE_FOR_NULL);
            
            return;
        }

        double aNumber = number instanceof Double || number instanceof Float ? number.doubleValue() : number.longValue();
        double aMaxValue = maxValue instanceof Double || maxValue instanceof Float ? maxValue.doubleValue() : maxValue.longValue();
        double aMinValue = minValue instanceof Double || minValue instanceof Float ? minValue.doubleValue() : minValue.longValue();

        if (aNumber > aMaxValue) {
            notification.addError(propertyName, MESSAGE_FOR_MAX_VALUE);
        }
        
        else if (aNumber < aMinValue) {
            notification.addError(propertyName, MESSAGE_FOR_MIN_VALUE);
        }

    }
    
    /**
     * Validates a number, verifying if it is out of min/max limits. If yes, an error will be added in the notification with a informative message and respective property name.
     * 
     * @param number the value that will be validated (can be null)
     * @param minValue minimum value to be accepted <strong>(exclusive)</strong>
     * @param propertyName name that will be included in notification errors (if there is)
     * @param maxValue maximum value to be accepted <strong>(exclusive)</strong>
     * @param notification notification object to store and get the errors (if there is)
     */
    public static void validateNumericalAttribute(int number, int minValue, String propertyName, int maxValue, NotificationContext notification) {
        if (number > maxValue) {
            notification.addError(propertyName, MESSAGE_FOR_MAX_VALUE);
        }
        
        else if (number < minValue) {
            notification.addError(propertyName, MESSAGE_FOR_MIN_VALUE);
        }
    }
    
    /**
     * Validates a number, verifying if it is out of min/max limits. If yes, an error will be added in the notification with a informative message and respective property name.
     * 
     * @param number the value that will be validated (can be null)
     * @param minValue minimum value to be accepted <strong>(exclusive)</strong>
     * @param propertyName name that will be included in notification errors (if there is)
     * @param maxValue maximum value to be accepted <strong>(exclusive)</strong>
     * @param notification notification object to store and get the errors (if there is)
     */
    public static void validateNumericalAttribute(double number, double minValue, String propertyName, double maxValue, NotificationContext notification) {
        if (number > maxValue) {
            notification.addError(propertyName, MESSAGE_FOR_MAX_VALUE);
        }
        
        else if (number < minValue) {
            notification.addError(propertyName, MESSAGE_FOR_MIN_VALUE);
        }
    }

    public static double round(int decimalPlates, double value) {
        if (decimalPlates < 0) {
            throw new IllegalArgumentException("Decimal plates must be greater than zero");
        }

        BigDecimal rounded = BigDecimal.valueOf(value).setScale(decimalPlates, RoundingMode.HALF_UP);

        return rounded.doubleValue();
    }
    
    public static float round(int decimalPlates, float value) {
        if (decimalPlates < 0) {
            throw new IllegalArgumentException("Decimal plates must be greater than zero");
        }

        BigDecimal rounded = BigDecimal.valueOf(value).setScale(decimalPlates, RoundingMode.HALF_UP);

        return rounded.floatValue();
    }
    
    public static double roundHalfEven(int decimalPlates, double value) {
        if (decimalPlates < 0) {
            throw new IllegalArgumentException("Decimal plates must be greater than zero");
        }

        BigDecimal rounded = BigDecimal.valueOf(value).setScale(decimalPlates, RoundingMode.HALF_EVEN);

        return rounded.doubleValue();
    }
    
    public static float roundHalfEven(int decimalPlates, float value) {
        if (decimalPlates < 0) {
            throw new IllegalArgumentException("Decimal plates must be greater than zero");
        }

        BigDecimal rounded = BigDecimal.valueOf(value).setScale(decimalPlates, RoundingMode.HALF_EVEN);

        return rounded.floatValue();
    }

    public static void validateExpirationDate(LocalDate date, String propertyName, NotificationContext notification) {
        if (propertyName == null || notification == null) {
            throw new NullPointerException("Property name and notification object mustn't be null. Verify these parameters.");
        }
        
        if (date == null) {
            notification.addError(propertyName, "The date is null.");
            return;
        }

        LocalDate todayDate = LocalDate.now();
        
        if (todayDate.equals(date)) {
            notification.addError(propertyName, "The expiration date is today!");
        }
        
        else if (todayDate.isAfter(date.minusDays(6))) {
            notification.addError(propertyName, "The expiration date shouldn't be to close of today (6 days or less).");
        } 

        else if (date.getYear() - todayDate.getYear() > 15) {
            notification.addError(propertyName, "You have sure that this expiration date is too far of current year? More than 15 years of expiration is much time");
        }       
    }
}
