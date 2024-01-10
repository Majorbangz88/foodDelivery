package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findByName(String driverName);

    Optional<Driver> findByPhoneNumber(String phoneNumber);
}
