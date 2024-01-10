package com.joel.foodDelivery.exceptions;

public class DriverAlreadyExistsException extends RuntimeException{

    public DriverAlreadyExistsException(String message) {
        super(message);
    }
}
