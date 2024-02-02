package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.OrderItem;
import com.joel.foodDelivery.data.models.Restaurant;
import com.joel.foodDelivery.data.models.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    private String username;
    private List<Restaurant> restaurants;
    private String driver;
    private String driverPhone;
    private List<OrderItem> items;
    private Status status;
    private LocalDateTime timeStamp;

}

