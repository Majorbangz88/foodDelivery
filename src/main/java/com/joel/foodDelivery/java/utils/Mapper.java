package com.joel.foodDelivery.java.utils;

import com.joel.foodDelivery.data.models.Address;
import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Restaurant;
import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;
import com.joel.foodDelivery.dtos.requests.RestaurantRegRequest;

public class Mapper {

    public static Customer map(CustomerRegistrationRequest registrationRequest) {
        Customer customer = new Customer();
        customer.setFirstName(registrationRequest.getFirstName());
        customer.setLastName(registrationRequest.getLastName());
        customer.setPhoneNumber(registrationRequest.getPhoneNumber());
        customer.setEmail(registrationRequest.getEmail());
        customer.setUsername(registrationRequest.getUsername());
        customer.setPassword(registrationRequest.getPassword());

        Address address = new Address();
        address.setHouseNumber(registrationRequest.getHouseNumber());
        address.setStreetName(registrationRequest.getStreetName());
        address.setClosestLandmark(registrationRequest.getClosestLandMark());
        address.setState(registrationRequest.getState());

        customer.setAddress(address);

        customer.setLocked(true);
        return customer;
    }

    public static Restaurant map(RestaurantRegRequest regRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(regRequest.getName());
        restaurant.setEmail(regRequest.getEmail());
        restaurant.setPassword(regRequest.getPassword());
        restaurant.setLocked(true);
        return restaurant;
    }
}
