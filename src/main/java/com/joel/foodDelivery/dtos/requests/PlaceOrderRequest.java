package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.Menu;
import com.joel.foodDelivery.data.models.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    private String username;
    private String email;
    private String restaurantName;
    private String driver;
    private String driverPhone;
    private List<String> menu;
    private Status status;
    private LocalDateTime timeStamp;

}
