package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AddToCartRequest {
    private String customerName;
    private List<Restaurant> restaurants;
    private List<String> menu;
    private int quantity;
    private Status status;
    private LocalDateTime timeStamp;
}
