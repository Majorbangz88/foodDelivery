package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedbackResponse {

    private String customer;
    private String restaurant;
    private String driver;
    private int rating;
    private String comment;
}
