package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Feedback")
public class Feedback {

    @Id
    private String Id;
    private String customer;
    private String restaurant;
    private String driver;
    private int rating;
    private String comment;
}
