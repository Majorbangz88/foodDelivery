package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.dtos.requests.*;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer registerCustomer(CustomerRegistrationRequest registrationRequest);

    Long count();

    void deleteAll();

    Customer unlock(LoginRequest loginRequest);

    Customer setLock(String name);

    PlaceOrderResponse createOrder(PlaceOrderRequest orderRequest);

    Order updateOrder(UpdateOrderRequest updateRequest);

    Optional<Customer> findByUsername(String username);

    Order cancelOrder(PlaceOrderRequest orderRequest);

    List<Order> findAllTransactionHistory();
}
