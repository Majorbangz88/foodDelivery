package com.joel.foodDelivery.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class AppConfig {

    @Value("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")
    private String mailApiKey;

    @Value("https://api.brevo.com/v3/smtp/email")
    private String mailServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


