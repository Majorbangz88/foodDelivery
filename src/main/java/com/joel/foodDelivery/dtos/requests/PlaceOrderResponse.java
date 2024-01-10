package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlaceOrderResponse {

    private String customer;
    private String email;
    private String restaurant;
    private String driver;
    private String driverPhone;
    private String menu;
    private Status status;
    private LocalDateTime timeStamp;
}
