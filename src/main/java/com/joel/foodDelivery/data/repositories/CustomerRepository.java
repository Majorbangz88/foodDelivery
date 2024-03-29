package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmailAndUsernameAndPhoneNumber(String email, String username, String phoneNumber);

    Optional<Customer> findByUsernameAndPassword(String username, String password);

    Optional<Customer> findByUsername(String username);
}
