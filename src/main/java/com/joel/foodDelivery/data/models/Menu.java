package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Menu")
public class Menu {

    @org.springframework.data.annotation.Id
    private String Id;
    private String itemName;
    private double price;
    private boolean availability;
}

