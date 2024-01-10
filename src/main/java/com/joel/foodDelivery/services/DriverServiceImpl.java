package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Driver;
import com.joel.foodDelivery.data.repositories.DriverRepository;
import com.joel.foodDelivery.dtos.requests.AddDriverRequest;
import com.joel.foodDelivery.dtos.requests.AddDriverResponse;
import com.joel.foodDelivery.exceptions.DriverAlreadyExistsException;
import com.joel.foodDelivery.exceptions.InvalidNameAndPhoneNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Optional<Driver> findByPhoneNumber(String phoneNumber) {
        return driverRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public AddDriverResponse addDriver(AddDriverRequest driverRequest) {
        validateNotNullInput(driverRequest);
        validateNewDriver(driverRequest.getPhoneNumber());
        Driver driver =new Driver();
        driver.setName(driverRequest.getName());
        driver.setPhoneNumber(driverRequest.getPhoneNumber());

        driverRepository.save(driver);

        AddDriverResponse response = new AddDriverResponse();
        response.setName(driver.getName());
        response.setPhoneNumber(driver.getPhoneNumber());

        return response;
    }

    private void validateNotNullInput(AddDriverRequest driverRequest) {
        boolean isNullName = driverRequest.getName() == null;
        boolean isNullPhoneNumber = driverRequest.getPhoneNumber() == null;
        if (isNullName || isNullPhoneNumber)
            throw new InvalidNameAndPhoneNumberException("Name or phone number is invalid");
    }

    private void validateNewDriver(String phoneNumber) {
        Optional<Driver> optionalDriver = driverRepository.findByPhoneNumber(phoneNumber);
        if (optionalDriver.isPresent())
            throw new DriverAlreadyExistsException(("This driver exists already!"));
    }

    @Override
    public Long count() {
        return driverRepository.count();
    }

    @Override
    public void deleteAll() {
        driverRepository.deleteAll();
    }
}
