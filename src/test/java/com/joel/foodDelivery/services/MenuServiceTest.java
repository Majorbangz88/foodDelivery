package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.CreateMenuRequest;
import com.joel.foodDelivery.dtos.requests.CreateMenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @BeforeEach
    public void setup() {
        menuService.deleteAll();
    }

    @Test
    public void testCreateMenu() {
        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant("Food 0'clock");
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(500.00);
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CreateMenuRequest menuRequested = new CreateMenuRequest();
        menuRequested.setRestaurant("Food 0'clock");
        menuRequested.setItemName("Abacha n'akpaka");
        menuRequested.setPrice(500.00);
        menuRequested.setAvailability(true);

        CreateMenuResponse menuResponses = menuService.createMenu(menuRequested);

        assertNotNull(menuResponses);

        CreateMenuRequest menuRequest1 = new CreateMenuRequest();
        menuRequest1.setRestaurant("Dolphins");
        menuRequest1.setItemName("Rice & Beans");
        menuRequest1.setPrice(500.00);
        menuRequest1.setAvailability(true);

        CreateMenuResponse menuResponse1 = menuService.createMenu(menuRequest1);

        assertNotNull(menuResponse1);

        CreateMenuRequest menuRequest2 = new CreateMenuRequest();
        menuRequest2.setRestaurant("Ntachi osa");
        menuRequest2.setItemName("Semo & Onugbu");
        menuRequest2.setPrice(500.00);
        menuRequest2.setAvailability(true);

        CreateMenuResponse menuResponse2 = menuService.createMenu(menuRequest2);

        assertNotNull(menuResponse2);

        assertThat(menuService.count(), is(4L));
    }
}