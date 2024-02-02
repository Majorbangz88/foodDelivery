package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.PlaceOrderResponse;
import com.joel.foodDelivery.dtos.requests.UpdateOrderRequest;

import java.util.List;

public interface OrderService {
    long count();

    void deleteAll();

    PlaceOrderResponse createOrder(PlaceOrderRequest orderRequest);

    Order updateOrder(UpdateOrderRequest updateOrder);

//    String trackOrderStatus(String id);

    void save(Order order);

    Order cancelOrder(PlaceOrderRequest orderRequest);

    List<Order> displayOrderHistory();

//    Order findOrderByCustomerName(String customerName);

    double calculateTotal(List<PlaceOrderRequest> orderRequests);
}
