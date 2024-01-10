package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Menu;
import com.joel.foodDelivery.dtos.requests.CreateMenuRequest;
import com.joel.foodDelivery.dtos.requests.CreateMenuResponse;

import java.util.Optional;

public interface MenuService {
    Optional<Menu> findByname(String menu);

    CreateMenuResponse createMenu(CreateMenuRequest menuRequest);

    Long count();

    void deleteAll();
}
