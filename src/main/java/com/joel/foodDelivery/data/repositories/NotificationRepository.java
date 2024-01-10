package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Notification;
import com.joel.foodDelivery.data.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRestaurantAndStatus(String restaurantName, Status status);
}
