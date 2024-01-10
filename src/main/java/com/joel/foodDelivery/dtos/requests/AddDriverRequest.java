package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDriverRequest {

    private String name;
    private String phoneNumber;
}
