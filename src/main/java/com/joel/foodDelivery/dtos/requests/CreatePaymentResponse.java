package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreatePaymentResponse<T> {

    private String status;
    private String message;
    private T data;
}
