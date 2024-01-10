package com.joel.foodDelivery.exceptions;

public class CustomerNotfoundException extends RuntimeException {
    public CustomerNotfoundException(String message) {
        super(message);
    }
}
