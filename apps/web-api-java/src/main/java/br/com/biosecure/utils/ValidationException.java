package br.com.biosecure.utils;

public class ValidationException extends IllegalArgumentException {
    private String invalidProperty;
    
    public ValidationException(String invalidPropertyName, String message) {
        super(message);
        
        this.invalidProperty = invalidPropertyName;
    }
    
    public String getInvalidProperty() {
        return invalidProperty;
    }
}