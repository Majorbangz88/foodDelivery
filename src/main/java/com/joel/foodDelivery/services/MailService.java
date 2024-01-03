package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.SendMailRequest;
import com.joel.foodDelivery.dtos.requests.SendMailResponse;

public interface MailService {
    SendMailResponse sendMail(SendMailRequest sendMailRequest);
}
