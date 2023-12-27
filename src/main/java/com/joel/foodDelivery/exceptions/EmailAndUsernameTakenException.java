package com.joel.foodDelivery.exceptions;

public class EmailAndUsernameTakenException extends RuntimeException{

    public EmailAndUsernameTakenException(String message) {
        super(message);
    }
}
