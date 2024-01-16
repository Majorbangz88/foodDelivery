package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.Recipient;
import com.joel.foodDelivery.dtos.requests.SendMailRequest;
import com.joel.foodDelivery.dtos.requests.SendMailResponse;
import com.joel.foodDelivery.dtos.requests.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        SendMailRequest sendMailRequest = buildMailRequest();
        SendMailResponse mailResponse = mailService.sendMail(sendMailRequest);
        assertNotNull(mailResponse);
        assertEquals(201, mailResponse.getStatusCode());
    }

    private static SendMailRequest buildMailRequest() {
        SendMailRequest mailRequest = new SendMailRequest();
        Sender sender = new Sender("food clock", "joel@gmail.com");
        List<Recipient> recipients = List.of(
                new Recipient("Joel", "joellegend8@gmail.com"));

        mailRequest.setSubject("Check this out");
        mailRequest.setHtmlContent("<p>Hello young man</p>");
        mailRequest.setSender(sender);
        mailRequest.setRecipients(recipients);
        return mailRequest;
    }


}