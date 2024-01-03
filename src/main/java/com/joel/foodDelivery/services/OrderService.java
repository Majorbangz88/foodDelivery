package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.UpdateOrderRequest;

public interface OrderService {
    long count();

    void deleteAll();

    Order createOrder(PlaceOrderRequest orderRequest);

    Order updateOrder(UpdateOrderRequest updateOrder);
}
