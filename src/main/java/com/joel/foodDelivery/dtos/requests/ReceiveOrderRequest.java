package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReceiveOrderRequest {
    private Order orderId;
    private Customer customer;
    private Restaurant restaurant;
    private Driver driver;
    private List<Menu> menu;
    private String status;
    private LocalDateTime timeStamp;
}
