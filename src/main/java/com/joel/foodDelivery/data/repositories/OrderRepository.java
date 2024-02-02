package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findOrderByCustomerName(String customer);

}