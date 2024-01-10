package com.joel.foodDelivery.exceptions;

public class IncorrectUsernameOrPasswordException extends RuntimeException {
    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }
}
