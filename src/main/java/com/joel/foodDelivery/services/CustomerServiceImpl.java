package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.repositories.CustomerRepository;
import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void registerCustomer(CustomerRegistrationRequest registrationRequest) {
        validateEmailAndUsername(registrationRequest.getEmail(), registrationRequest.getUsername());
        Customer customer = new Customer();
        customer.setEmail(registrationRequest.getEmail());
        customer.setUsername(registrationRequest.getUsername());
        customer.setPassword(registrationRequest.getPassword());
        customerRepository.save(customer);
    }

    private void validateEmailAndUsername(String email, String username) {
        Optional<Customer> customer = customerRepository.FindCustomerByEmailAndUsername(email, username);
        if (customer.isPresent())
            throw new EmailAndUsernameTakenException("Email or username taken");
    }

    @Override
    public Long count() {
        return customerRepository.count();
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();;
    }
}
