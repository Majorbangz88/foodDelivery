package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRegRequest {

    private String name;
    private String email;
    private String password;
}
