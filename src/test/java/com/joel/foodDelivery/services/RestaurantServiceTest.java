package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Restaurant;
import com.joel.foodDelivery.dtos.requests.*;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MenuService menuService;

    @BeforeEach
    public void setup() {
        restaurantService.deleteAll();
        menuService.deleteAll();
    }

    @Test
    public void testThatRestaurantsCanGetOnThePlatform() {
        Restaurant restaurant = registerRestaurant("Food 0'clock", "food0clock@gmail.com", "password");
        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(500.00);
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        assertNotNull(restaurant.getId());

        restaurant = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        Restaurant restaurant1 = registerRestaurant("Dolphins", "dolphinseateries@gmail.com", "passwords");
        assertNotNull(restaurant1.getId());

        CreateMenuRequest menuRequest1 = new CreateMenuRequest();
        menuRequest1.setRestaurant(restaurant1.getName());
        menuRequest1.setItemName("Rice & Beans");
        menuRequest1.setPrice(500.00);
        menuRequest1.setAvailability(true);

        CreateMenuResponse menuResponse1 = menuService.createMenu(menuRequest1);

        assertNotNull(menuResponse1);

        restaurant1 = restaurantService.setLocked(restaurant1.getName());
        assertTrue(restaurant1.isLocked());

        Restaurant restaurant2 = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertNotNull(restaurant2.getId());

        CreateMenuRequest menuRequest2 = new CreateMenuRequest();
        menuRequest2.setRestaurant(restaurant2.getName());
        menuRequest2.setItemName("Semo & Onugbu");
        menuRequest2.setPrice(500.00);
        menuRequest2.setAvailability(true);

        CreateMenuResponse menuResponse2 = menuService.createMenu(menuRequest2);

        assertNotNull(menuResponse2);

        restaurant2  = restaurantService.setLocked(restaurant2.getName());
        assertTrue(restaurant2.isLocked());

        assertThat(restaurantService.count(), is(3L));
        assertThat(menuService.count(), is(3L));
    }


    @Test
    public void testThatRestaurantIsUnique() {
        Restaurant restaurant = registerRestaurant("Food 0'clock", "food0clock@gmail.com", "password");
        assertNotNull(restaurant.getId());

        restaurant = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        Restaurant restaurant1 = registerRestaurant("Dolphins", "dolphinseateries@gmail.com", "passwords");
        assertNotNull(restaurant1.getId());

        Restaurant restaurant2 = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertNotNull(restaurant2.getId());

        assertThat(restaurantService.count(), is(3L));

        restaurant1 = restaurantService.setLocked(restaurant1.getName());
        assertTrue(restaurant1.isLocked());

        RestaurantRegRequest regRequest1 = new RestaurantRegRequest();
        regRequest1.setName("Dolphins");
        regRequest1.setEmail("dolphinseateries@gmail.com");
        regRequest1.setPassword("password");

        try {
            restaurantService.registerRestaurant(regRequest1);
        } catch (EmailAndUsernameTakenException e) {
            System.out.println(e.getMessage());
        }
    }

    private Restaurant registerRestaurant(String name, String email, String password) {
        RestaurantRegRequest regRequest = new RestaurantRegRequest();
        regRequest.setName(name);
        regRequest.setEmail(email);
        regRequest.setPassword(password);
        restaurantService.registerRestaurant(regRequest);
        return restaurantService.findByName(name).orElseThrow();
    }

    @Test
    public void testThatWhenRestaurantLogsIn_AppIsUnlocked() {
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest = new RestaurantLoginRequest();
        loginRequest.setEmail(restaurant.getEmail());
        loginRequest.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest);
        assertFalse(restaurant.isLocked());
    }

    @Test
    public void testThatRestaurantReceivesOrderFromCustomer() {
        Restaurant restaurant = registerRestaurant("Food 0'clock", "food0clock@gmail.com", "password");
        assertNotNull(restaurant.getId());

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest = new RestaurantLoginRequest();
        loginRequest.setEmail(restaurant.getEmail());
        loginRequest.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest);
        assertFalse(restaurant.isLocked());

        Restaurant restaurant1 = registerRestaurant("Dolphins", "dolphinseateries@gmail.com", "passwords");
        assertNotNull(restaurant1.getId());

        restaurant1  = restaurantService.setLocked(restaurant1.getName());
        assertTrue(restaurant1.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant1.getEmail());
        loginRequest1.setPassword(restaurant1.getPassword());

        restaurant1 = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant1.isLocked());

        Restaurant restaurant2 = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(3L));

        restaurant2  = restaurantService.setLocked(restaurant2.getName());
        assertTrue(restaurant2.isLocked());

        RestaurantLoginRequest loginRequest2 = new RestaurantLoginRequest();
        loginRequest2.setEmail(restaurant2.getEmail());
        loginRequest2.setPassword(restaurant2.getPassword());

        restaurant2 = restaurantService.unlock(loginRequest2);
        assertFalse(restaurant2.isLocked());
    }

}



