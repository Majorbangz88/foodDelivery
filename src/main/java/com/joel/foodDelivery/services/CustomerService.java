package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;

public interface CustomerService {

    public void registerCustomer(CustomerRegistrationRequest registrationRequest);

    Long count();

    void deleteAll();
}
