package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Order")
public class Order {

    @Id
    private String id;
    private String customerId;
    private String customerName;
    private List<Restaurant> restaurants;
    private List<Menu> menu;
//    private int quantity;
    private String email;
    private Driver driver;
    private Status status;
    private BigDecimal total;
    private LocalDateTime timeStamp;
}

