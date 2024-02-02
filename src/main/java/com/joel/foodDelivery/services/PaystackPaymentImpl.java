package com.joel.foodDelivery.services;

import com.joel.foodDelivery.config.PaymentConfig;
import com.joel.foodDelivery.dtos.requests.CreatePaymentRequest;
import com.joel.foodDelivery.dtos.requests.CreatePaymentResponse;
import com.joel.foodDelivery.dtos.requests.PaystackTransactionVerificationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;


import static com.joel.foodDelivery.utils.AppUtils.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static java.net.URI.create;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AllArgsConstructor
@Service
@Component
@Slf4j
public class PaystackPaymentImpl implements PaymentService{

    private final PaymentConfig paymentConfig;
    private final RestTemplate restTemplate;

    @Override
    public CreatePaymentResponse<?> pay(CreatePaymentRequest paymentRequest) {
        String apiKey = paymentConfig.getPaystackApiKey();
        URI uri = create(paymentConfig.getPaystackApiUrl());
        RequestEntity<CreatePaymentRequest> data = buildPaymentRequest(paymentRequest, apiKey, uri);
        log.info("req--> {} ", data);
        var response = restTemplate.postForEntity(uri, data, CreatePaymentResponse.class);
        System.out.println(response);
        return response.getBody();
    }

    @Override
    public String verifyPaymentFor(String transactionReference) {
        String url = paymentConfig.getPaystackVerificationUrl()+transactionReference;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION, BEARER.concat(paymentConfig.getPaystackApiKey()));
        RequestEntity<?> request = new RequestEntity<>(httpHeaders, GET, null);
        var response = restTemplate.exchange(url, GET, request, PaystackTransactionVerificationResponse.class);
        return Objects.requireNonNull(response.getBody()).getMessage();
    }

    private RequestEntity<CreatePaymentRequest> buildPaymentRequest(CreatePaymentRequest paymentRequest, String apiKey, URI uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "Bearer ".concat(apiKey));
        headers.setContentType(APPLICATION_JSON);
        RequestEntity<CreatePaymentRequest> data =
                new RequestEntity<>(paymentRequest, headers, POST, uri);
        return data;
    }
}
