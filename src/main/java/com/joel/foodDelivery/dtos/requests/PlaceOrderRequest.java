package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Driver;
import com.joel.foodDelivery.data.models.Menu;
import com.joel.foodDelivery.data.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    private Customer customer;
    private Restaurant restaurant;
    private Driver driver;
    private List<Menu> menu;
    private String status;
    private LocalDateTime timeStamp;

}
