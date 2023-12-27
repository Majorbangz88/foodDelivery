package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Feedback")
public class Feedback {

    @org.springframework.data.annotation.Id
    private String Id;
    private Customer customer;
    private Restaurant restaurant;
    private Driver driver;
    private int rating;
    private String comment;
}
