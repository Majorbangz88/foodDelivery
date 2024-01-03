package com.joel.foodDelivery.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SendMailResponse {
    private int statusCode;
    private String messageId;
}
