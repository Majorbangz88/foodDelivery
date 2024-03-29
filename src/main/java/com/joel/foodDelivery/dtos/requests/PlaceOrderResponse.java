package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlaceOrderResponse {

    private String username;
    private List<Restaurant> restaurants;
    private String driver;
    private String driverPhone;
    private List<OrderItem> items;
    private Status status;
    private LocalDateTime timeStamp;
}
