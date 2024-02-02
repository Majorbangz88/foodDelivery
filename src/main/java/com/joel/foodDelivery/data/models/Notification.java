package com.joel.foodDelivery.data.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Notification {

    private String sender;
    private String message;
    private List<OrderItem> menu;
    private String driver;
    private List<Restaurant> restaurant;
    private Status status;
    private LocalDateTime dateTime;

}
