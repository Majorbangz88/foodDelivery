package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateMenuResponse {

    private String restaurant;
    private String itemName;
    private BigDecimal price;
    private boolean availability;
}
