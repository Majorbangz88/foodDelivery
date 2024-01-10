package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Menu;
import com.joel.foodDelivery.data.repositories.MenuRepository;
import com.joel.foodDelivery.dtos.requests.CreateMenuRequest;
import com.joel.foodDelivery.dtos.requests.CreateMenuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;
    @Override
    public Optional<Menu> findByname(String menuName) {
        return menuRepository.findByItemName(menuName);
    }

    @Override
    public CreateMenuResponse createMenu(CreateMenuRequest menuRequest) {
        Menu menu = new Menu();
        menu.setRestaurant(menuRequest.getRestaurant());
        menu.setItemName(menuRequest.getItemName());
        menu.setPrice(menuRequest.getPrice());
        menu.setAvailability(menuRequest.isAvailability());

        menuRepository.save(menu);

        CreateMenuResponse response = new CreateMenuResponse();
        response.setRestaurant(menu.getRestaurant());
        response.setItemName(menuRequest.getItemName());
        response.setPrice(menuRequest.getPrice());
        response.setAvailability(menuRequest.isAvailability());

        return response;
    }

    @Override
    public Long count() {
        return menuRepository.count();
    }

    @Override
    public void deleteAll() {
        menuRepository.deleteAll();
    }
}
