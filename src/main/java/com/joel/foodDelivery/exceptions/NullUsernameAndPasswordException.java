package com.joel.foodDelivery.exceptions;

public class NullUsernameAndPasswordException extends RuntimeException {
    public NullUsernameAndPasswordException(String message) {
        super(message);
    }
}
