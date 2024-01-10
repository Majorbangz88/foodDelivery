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

    @Value("${brevo.api.key}")
    private String mailApiKey;

    @Value("${brevo.api.url}")
    private String mailServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


