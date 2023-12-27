package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}
