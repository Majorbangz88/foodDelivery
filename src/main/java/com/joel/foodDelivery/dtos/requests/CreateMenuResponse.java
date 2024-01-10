package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMenuResponse {

    private String restaurant;
    private String itemName;
    private double price;
    private boolean availability;
}
