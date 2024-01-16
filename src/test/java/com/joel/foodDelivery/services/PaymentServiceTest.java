package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.CreatePaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testPay() {
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
        paymentRequest.setAmount(TEN);
        paymentRequest.setEmail("johnlegend@gmail.com");

        var response = paymentService.pay(paymentRequest);
//        log.info("payment response---->{}", response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testVerifyPayment() {
        String transactionReference = "";
        String transactionStatus = paymentService.verifyPaymentFor(transactionReference);
        assertThat(transactionStatus).containsIgnoringCase("success");
    }
}