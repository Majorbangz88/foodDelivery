package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Restaurant;
import com.joel.foodDelivery.dtos.requests.*;

import java.util.Optional;

public interface RestaurantService {
    RestaurantRegResponse registerRestaurant(RestaurantRegRequest regRequest);

    Long count();

    void deleteAll();

    Restaurant setLocked(String name);

    Optional<Restaurant> findByName(String name);

    Restaurant unlock(RestaurantLoginRequest loginRequest);

//    Order receiveOrder(PlaceOrderRequest placeOrderRequest);
}
