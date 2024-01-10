package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByNameAndPassword(String name, String password);

    Optional<Restaurant> findByName(String name);

    Optional<Restaurant> findByEmail(String email);
}
