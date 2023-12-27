package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends MongoRepository<Driver, String> {
}
