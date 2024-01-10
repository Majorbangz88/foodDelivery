package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MenuRepository extends MongoRepository<Menu, String> {
    Optional<Menu> findByItemName(String menuName);
}
