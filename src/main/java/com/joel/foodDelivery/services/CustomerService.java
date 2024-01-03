package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;
import com.joel.foodDelivery.dtos.requests.LoginRequest;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.UpdateOrderRequest;

import java.util.Optional;

public interface CustomerService {

    public void registerCustomer(CustomerRegistrationRequest registrationRequest);

    Long count();

    void deleteAll();

    Customer isLocked(LoginRequest loginRequest);

    Customer setLock();

    Order createOrder(PlaceOrderRequest orderRequest);

    Order updateOrder(UpdateOrderRequest updateRequest);

    Optional<Customer> findByUsername(String username);
}
