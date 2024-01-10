package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantLoginRequest {

    private String email;
    private String password;
}
