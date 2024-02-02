package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Feedback")
public class Feedback {

    @Id
    private String Id;
    private String customer;
    private List<Restaurant> restaurant;
    private String driver;
    private int rating;
    private String comment;
}
