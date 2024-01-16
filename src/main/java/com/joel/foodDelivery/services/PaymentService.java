package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.CreatePaymentRequest;

public interface PaymentService {
    Object pay(CreatePaymentRequest paymentRequest);

    String verifyPaymentFor(String transactionReference);
}
