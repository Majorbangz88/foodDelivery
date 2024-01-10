package com.joel.foodDelivery.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaymentConfig {

    @Value("${paystack.api.key}")
    private String paystackApiKey;
    @Value("${paystack.api.url}")
    private String paystackApiUrl;
    @Value("${paystack.verification.url}")
    private String paystackVerificationUrl;

}
