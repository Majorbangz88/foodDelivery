package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customerService.deleteAll();
    }

    @Test
    public void testThatNewCustomerCanRegisterOnTheApp() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));
    }

    @Test
    public void testThatCustomerEmailAndUsernameAreUnique() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        CustomerRegistrationRequest registrationRequest2 = new CustomerRegistrationRequest();
        registrationRequest2.setEmail("johnlegend@gmail.com");
        registrationRequest2.setUsername("username");
        registrationRequest2.setPassword("password");
        assertThrows(EmailAndUsernameTakenException.class, ()-> customerService.registerCustomer(registrationRequest2));
    }
}