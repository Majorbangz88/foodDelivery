package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
