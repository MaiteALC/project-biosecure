package br.com.biosecure.utils;

import java.util.ArrayList;

public class NotificationContext {
    ArrayList<ValidationException> exceptions = new ArrayList<>();

    public NotificationContext() {}

    public void addError(String propertyName, String message) {
        this.exceptions.add(new ValidationException(propertyName, message));
    }

    public boolean hasErrors() {
        return this.exceptions.size() > 0;
    }

    public ArrayList<ValidationException> getErrors() {
        return this.exceptions;
    }
}   
