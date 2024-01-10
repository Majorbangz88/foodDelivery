package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Notification;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.PlaceOrderResponse;

public interface NotificationService {
    Notification sendNotification(PlaceOrderRequest request);

    void deleteAll();
}
