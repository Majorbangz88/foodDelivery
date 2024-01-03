package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.*;
import com.joel.foodDelivery.dtos.requests.CustomerRegistrationRequest;
import com.joel.foodDelivery.dtos.requests.LoginRequest;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.UpdateOrderRequest;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import com.joel.foodDelivery.exceptions.OrderNotExistException;
import com.joel.foodDelivery.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        customerService.deleteAll();
        orderService.deleteAll();
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

    @Test
    public void testThatRegisteredUserCanLogInWhenUsernameAndPasswordAreCorrect() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.isLocked(loginRequest);
        assertFalse(customer.isLocked());
    }

    @Test
    public void testThatExceptionIsThrownWhenUsernameOrPasswordIsIncorrect() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user_name");
        loginRequest.setPassword("password");
        assertThrows(UserNotFoundException.class, ()-> customerService.isLocked(loginRequest));
    }

    @Test
    public void testThatExceptionIsThrownWhenUsernameOrPasswordIsNull() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("");
        assertThrows(UserNotFoundException.class, ()-> customerService.isLocked(loginRequest));
    }

    @Test
    public void testThatAppIsLockedWhenUserLogsOut() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        Customer loginStatus = customerService.setLock();
        assertTrue(loginStatus.isLocked());
    }

//    @Test
//    public void testThatCustomerCanPlaceOrder() {
//        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
//        registrationRequest.setEmail("johnlegend@gmail.com");
//        registrationRequest.setUsername("username");
//        registrationRequest.setPassword("password");
//        customerService.registerCustomer(registrationRequest);
//        assertThat(customerService.count(), is(1L));
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("username");
//        loginRequest.setPassword("password");
//        Customer customer = customerService.isLocked(loginRequest);
//        assertFalse(customer.isLocked());
//
//        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
//        customer.setUsername(registrationRequest.getUsername());
//        customer.setEmail(registrationRequest.getEmail());
//        orderRequest.setCustomer(customer);
//        orderRequest.setTimeStamp(LocalDateTime.now());
//        orderRequest.setStatus("Available");
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("Food 0'clock");
//        restaurant.setMenu(List.of(new Menu("1", "Rice", 500.00, true)));
//        orderRequest.setRestaurant(restaurant);
//        Driver driver = new Driver();
//        driver.setName("Jake");
//        orderRequest.setDriver(driver);
//        customerService.createOrder(orderRequest);
//        assertThat(orderService.count(), is(1L));
//    }

    @Test
    public void testCustomerCanPlaceOrder() {
        Customer customer = registerCustomer();

        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = createOrderRequest(customer);
        customerService.createOrder(orderRequest);

        assertThat(customerService.count(), is(1L));
        assertThat(orderService.count(), is(1L));
    }

    private Customer registerCustomer() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        return customerService.findByUsername("username").orElseThrow();
    }

    private PlaceOrderRequest createOrderRequest(Customer customer) {
        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setCustomer(customer);
        orderRequest.setTimeStamp(LocalDateTime.now());
        orderRequest.setStatus("Available");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Food 0'clock");
        restaurant.setMenu(List.of(new Menu("1", "Rice", 500.00, true)));
        orderRequest.setRestaurant(restaurant);

        Driver driver = new Driver();
        driver.setName("Jake");
        orderRequest.setDriver(driver);

        return orderRequest;
    }


//    @Test
//    public void testThatOrderCanBeUpdated() {
//        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
//        registrationRequest.setEmail("johnlegend@gmail.com");
//        registrationRequest.setUsername("username");
//        registrationRequest.setPassword("password");
//        customerService.registerCustomer(registrationRequest);
//        assertThat(customerService.count(), is(1L));
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("username");
//        loginRequest.setPassword("password");
//        Customer customer = customerService.isLocked(loginRequest);
//        assertFalse(customer.isLocked());
//
//        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
//        customer.setUsername(registrationRequest.getUsername());
//        customer.setEmail(registrationRequest.getEmail());
//        orderRequest.setCustomer(customer);
//        orderRequest.setTimeStamp(LocalDateTime.now());
//        orderRequest.setStatus("Available");
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("Food 0'clock");
//        restaurant.setMenu(List.of(new Menu("1", "Rice", 500.00, true)));
//        orderRequest.setRestaurant(restaurant);
//        Driver driver = new Driver();
//        driver.setName("Jake");
//        orderRequest.setDriver(driver);
//        customerService.createOrder(orderRequest);
////        assertThat(orderService.count(), is(1L));
//
//        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
//        customer.setUsername(registrationRequest.getUsername());
//        customer.setEmail(registrationRequest.getEmail());
//        updateRequest.setCustomer(customer);
//        updateRequest.setTimeStamp(LocalDateTime.now());
//        updateRequest.setStatus("Available");
////        Restaurant restaurant = new Restaurant();
//        restaurant.setName("Food Bank");
//        restaurant.setMenu(List.of(new Menu("1", "Beans", 500.00, true)));
//        updateRequest.setRestaurant(restaurant);
////        Driver driver = new Driver();
//        driver.setName("Jade");
//        updateRequest.setDriver(driver);
//        customerService.updateOrder(updateRequest);
//        assertThat(orderService.count(), is(1L));
//    }
@Test
public void testOrderCanBeUpdated() {
    Customer customer = registerCustomerAndPlaceOrder();

    assertFalse(customer.isLocked());

    UpdateOrderRequest updateRequest = createUpdateOrderRequest(customer);
    customerService.updateOrder(updateRequest);

    assertThat(customerService.count(), is(1L));
    assertThat(orderService.count(), is(1L));
}

    private Customer registerCustomerAndPlaceOrder() {
        Customer customer = registerCustomer();


        // Order Placement
        PlaceOrderRequest orderRequest = createOrderRequest(customer);
        customerService.createOrder(orderRequest);
        assertThat(orderService.count(), is(1L));

        return customer;
    }

    private UpdateOrderRequest createUpdateOrderRequest(Customer customer) {
        // Order Update Request
        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        updateRequest.setCustomer(customer);
        updateRequest.setTimeStamp(LocalDateTime.now());
        updateRequest.setStatus("Available");

        // Updated Restaurant and Driver details
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setName("Food Bank");
        updatedRestaurant.setMenu(List.of(new Menu("1", "Beans", 500.00, true)));
        updateRequest.setRestaurant(updatedRestaurant);

        Driver updatedDriver = new Driver();
        updatedDriver.setName("Jade");
        updateRequest.setDriver(updatedDriver);

        return updateRequest;
    }


    @Test
    public void testThatExceptionIsThrownWhen_UpdateAttemptIsMade_ButUpdateNotFound() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.isLocked(loginRequest);
        assertFalse(customer.isLocked());

        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        customer.setUsername(registrationRequest.getUsername());
        customer.setEmail(registrationRequest.getEmail());
        updateRequest.setCustomer(customer);
        updateRequest.setTimeStamp(LocalDateTime.now());
        updateRequest.setStatus("Available");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Food Bank");
        restaurant.setMenu(List.of(new Menu("1", "Beans", 500.00, true)));
        updateRequest.setRestaurant(restaurant);
        Driver driver = new Driver();
        driver.setName("Jade");
        updateRequest.setDriver(driver);

        assertThrows(OrderNotExistException.class, ()-> customerService.updateOrder(updateRequest));
    }

    @Test
    public void testThatCustomerCanTrackOrder() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.isLocked(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        customer.setUsername(registrationRequest.getUsername());
        customer.setEmail(registrationRequest.getEmail());
        orderRequest.setCustomer(customer);
        orderRequest.setTimeStamp(LocalDateTime.now());
        orderRequest.setStatus("Available");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Food 0'clock");
        restaurant.setMenu(List.of(new Menu("1", "Rice", 500.00, true)));
        orderRequest.setRestaurant(restaurant);
        Driver driver = new Driver();
        driver.setName("Jake");
        orderRequest.setDriver(driver);
        customerService.createOrder(orderRequest);
        assertThat(orderService.count(), is(1L));
    }
}