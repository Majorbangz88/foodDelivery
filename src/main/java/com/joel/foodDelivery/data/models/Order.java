package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Order")
public class Order {

    @Id
    private String Id;
    private Customer customer;
    private Restaurant restaurant;
    private Driver driver;
    private List<Menu> menu;
    private String status;
}

