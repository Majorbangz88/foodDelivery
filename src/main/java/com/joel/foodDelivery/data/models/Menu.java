package com.joel.foodDelivery.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("Menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    private String Id;
    private String restaurant;
    private String itemName;
    private BigDecimal price;
    private boolean availability;
}

