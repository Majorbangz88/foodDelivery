package com.joel.foodDelivery.data.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Menu menu;
    private int quantity;
}
