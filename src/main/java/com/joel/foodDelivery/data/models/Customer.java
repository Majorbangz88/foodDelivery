package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Customer")
public class Customer {

    @Id
    private String id;
    private String email;
    private String username;
    private String password;
}
