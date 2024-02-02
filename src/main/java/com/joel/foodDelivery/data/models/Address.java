package com.joel.foodDelivery.data.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {

    private String houseNumber;
    private String streetName;
    private String closestLandmark;
    private String state;
}
