package com.joel.foodDelivery.java.utils;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;

public class Mapper {

    public static Customer map(CustomerRegistrationRequest registrationRequest) {
        Customer customer = new Customer();
        customer.setEmail(registrationRequest.getEmail());
        customer.setUsername(registrationRequest.getUsername());
        customer.setPassword(registrationRequest.getPassword());
        customer.setLocked(true);
        return customer;
    }
}
