package com.joel.foodDelivery.controller;

import com.joel.foodDelivery.dtos.requests.CreateMenuRequest;
import com.joel.foodDelivery.dtos.requests.RestaurantRegRequest;
import com.joel.foodDelivery.services.MenuService;
import com.joel.foodDelivery.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MenuService menuService;

    @PostMapping(path = "api/v1/registerRestaurant")
    public String registerRestaurant(@RequestBody RestaurantRegRequest registerRequest) {
        try {
            restaurantService.registerRestaurant(registerRequest);
            return "Register successful";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    public String createMenu(@RequestBody CreateMenuRequest createMenuRequest) {
        try {
            menuService.createMenu(createMenuRequest);
            return "Menu created successfully";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
