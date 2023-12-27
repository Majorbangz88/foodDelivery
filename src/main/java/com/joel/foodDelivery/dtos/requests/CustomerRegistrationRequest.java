package com.joel.foodDelivery.dtos.requests;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {

    private String email;
    private String username;
    private String password;
}
