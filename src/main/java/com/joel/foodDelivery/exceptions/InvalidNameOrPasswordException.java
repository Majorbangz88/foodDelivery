package com.joel.foodDelivery.exceptions;

public class InvalidNameOrPasswordException extends RuntimeException {
    public InvalidNameOrPasswordException(String message) {
        super(message);
    }
}
