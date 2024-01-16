package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.*;
import com.joel.foodDelivery.dtos.requests.*;
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
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    NotificationService notificationService;

    @BeforeEach
    public void setup() {
        customerService.deleteAll();
        orderService.deleteAll();
        feedbackService.deleteAll();
        notificationService.deleteAll();
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
        Customer customer = customerService.unlock(loginRequest);
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
        assertThrows(UserNotFoundException.class, ()-> customerService.unlock(loginRequest));
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
        assertThrows(UserNotFoundException.class, ()-> customerService.unlock(loginRequest));
    }

    @Test
    public void testThatAppIsLockedWhenUserLogsOut() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        Customer customer = customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer1 = customerService.unlock(loginRequest);
        assertFalse(customer1.isLocked());
        Customer customer2 = customerService.setLock(customer1.getUsername());

        assertTrue(customer2.isLocked());
    }

    @Test
    public void testThatCustomerCanPlaceOrder() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(registrationRequest.getUsername());
        orderRequest.setRestaurantName("Food 0'clock");
        orderRequest.setEmail(registrationRequest.getEmail());
        orderRequest.setMenu(List.of("Rice", "Abacha n'akpaka"));
        orderRequest.setDriver("Jake");
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse response = customerService.createOrder(orderRequest);
        notificationService.sendNotification(orderRequest);

        assertNotNull(response);
        assertThat(orderService.count(), is(1L));
    }

    @Test
    public void testThatOrderCanBeCanceled() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(registrationRequest.getUsername());
        orderRequest.setEmail(registrationRequest.getEmail());
        orderRequest.setRestaurantName("Dolphins");
        orderRequest.setMenu(List.of("Rice & Beans"));
        orderRequest.setDriver("Jade");
        orderRequest.setDriverPhone("08034239899");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse response = customerService.createOrder(orderRequest);
        assertNotNull(response);

        assertThat(orderService.count(), is(1L));

        Order order = customerService.cancelOrder(orderRequest);

        assertThat(order.getStatus(), is(Status.CANCELED));
    }


    @Test
    public void testThatExceptionIsThrownWhen_UpdateAttemptIsMade_ButOrderNotFound() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        updateRequest.setName(registrationRequest.getUsername());
        updateRequest.setStatus(Status.DELIVERED);
        updateRequest.setTimeStamp(LocalDateTime.now());
//        UpdateOrderResponse response = customerService.updateOrder(updateRequest);

//        assertNotNull(response);
        assertThrows(OrderNotExistException.class, ()-> customerService.updateOrder(updateRequest));
    }

    @Test
    public void testThatCustomerCanProvideFeedback() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(registrationRequest.getUsername());
        orderRequest.setRestaurantName("Food 0'clock");
        orderRequest.setEmail(registrationRequest.getEmail());
        orderRequest.setMenu(List.of("Rice"));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse response = customerService.createOrder(orderRequest);
        notificationService.sendNotification(orderRequest);
        assertNotNull(response);
        assertThat(orderService.count(), is(1L));

        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
        updateOrderRequest.setName(registrationRequest.getUsername());
        updateOrderRequest.setStatus(Status.DELIVERED);
        updateOrderRequest.setTimeStamp(LocalDateTime.now());

        Order order = customerService.updateOrder(updateOrderRequest);
        assertThat(order.getStatus(), is(Status.DELIVERED));

        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setCustomer(registrationRequest.getUsername());
        feedbackRequest.setRestaurant(orderRequest.getRestaurantName());
        feedbackRequest.setDriver(orderRequest.getDriver());
        feedbackRequest.setRating(10);
        feedbackRequest.setComment("Timely delivery plus tasty food! Awesome experience");
        FeedbackResponse response1 = feedbackService.provideFeedback(feedbackRequest);

        assertNotNull(response1);
        assertThat(feedbackService.count(), is(1L));
    }

    @Test
    public void testThatCustomerCanFindAllOrderHistory() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(registrationRequest.getUsername());
        orderRequest.setRestaurantName("Food 0'clock");
        orderRequest.setEmail(registrationRequest.getEmail());
        orderRequest.setMenu(List.of("Rice"));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse response = customerService.createOrder(orderRequest);
        notificationService.sendNotification(orderRequest);
        assertNotNull(response);
        assertThat(orderService.count(), is(1L));

        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
        updateOrderRequest.setName(registrationRequest.getUsername());
        updateOrderRequest.setStatus(Status.DELIVERED);
        updateOrderRequest.setTimeStamp(LocalDateTime.now());

        Order order = customerService.updateOrder(updateOrderRequest);
        assertThat(order.getStatus(), is(Status.DELIVERED));

        List<Order> allOrders = customerService.findAllTransactionHistory();

        assertNotNull(allOrders);
//        assertThat(allOrders).isNotNull();
    }

    @Test
    public void testThatOrderTotalCanBeCalculated() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurantName("Food 0'clock");
        orderRequest.setEmail(customer.getEmail());
        orderRequest.setMenu(List.of("Rice", "Abacha n'aka"));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse response = customerService.createOrder(orderRequest);
    }


}