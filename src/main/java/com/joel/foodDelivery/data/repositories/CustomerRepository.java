package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmailAndUsername(String email, String username);
}
