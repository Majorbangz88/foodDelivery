package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Driver;
import com.joel.foodDelivery.dtos.requests.AddDriverRequest;
import com.joel.foodDelivery.dtos.requests.AddDriverResponse;

import java.util.Optional;

public interface DriverService {
    Optional<Driver> findByPhoneNumber(String phoneNumber);

    AddDriverResponse addDriver(AddDriverRequest driverRequest);

    Long count();

    void deleteAll();
}
