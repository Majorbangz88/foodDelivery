package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Order")
public class Order {

    @Id
    private String id;
    private String customerName;
    private String email;
    private String restaurantName;
    private Driver driver;
    private List<Menu> menu;
    private Status status;
    private LocalDateTime timeStamp;
}

