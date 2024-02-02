package com.joel.foodDelivery.dtos.requests;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Driver;
import com.joel.foodDelivery.data.models.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedbackRequest {

    private String customer;
    private List<Restaurant> restaurant;
    private String driver;
    private int rating;
    private String comment;
}
