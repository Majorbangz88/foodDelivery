package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Restaurant")
public class Restaurant {

    @Id
    private String Id;
    private String name;
    private List<Menu> menu;
}
