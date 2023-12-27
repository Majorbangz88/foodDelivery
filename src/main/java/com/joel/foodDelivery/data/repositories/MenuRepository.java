package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu, String> {
}
