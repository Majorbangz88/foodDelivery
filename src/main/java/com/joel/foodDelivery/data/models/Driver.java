package com.joel.foodDelivery.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Driver")
public class Driver {

    @Id
    private String Id;
    private String name;
    private String phoneNumber;
}
