package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Customer")
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private boolean isLocked;

}
