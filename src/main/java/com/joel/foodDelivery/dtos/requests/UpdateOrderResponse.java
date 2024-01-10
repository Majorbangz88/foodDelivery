package com.joel.foodDelivery.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateOrderResponse {

    private String status;
    private LocalDateTime dateTime;

}
