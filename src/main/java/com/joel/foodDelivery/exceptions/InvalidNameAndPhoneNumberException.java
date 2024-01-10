package com.joel.foodDelivery.exceptions;

public class InvalidNameAndPhoneNumberException extends RuntimeException {
    public InvalidNameAndPhoneNumberException(String message) {

        super(message);
    }
}
