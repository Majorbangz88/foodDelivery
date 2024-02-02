package com.joel.foodDelivery.dtos.requests;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {

    private String firstName;
    private String lastName;
    private String houseNumber;
    private String streetName;
    private String closestLandMark;
    private String State;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
}
