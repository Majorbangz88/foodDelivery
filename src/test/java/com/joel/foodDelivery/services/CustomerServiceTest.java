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

import java.math.BigDecimal;
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
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    MenuService menuService;

    @BeforeEach
    public void setup() {
        customerService.deleteAll();
        orderService.deleteAll();
        feedbackService.deleteAll();
        notificationService.deleteAll();
        restaurantService.deleteAll();
        menuService.deleteAll();
    }

    @Test
    public void testThatNewCustomerCanRegisterOnTheApp() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");

        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));
    }

    @Test
    public void testThatCustomerEmailAndUsernameAreUnique() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
        assertThat(customerService.count(), is(1L));

        CustomerRegistrationRequest registrationRequest2 = new CustomerRegistrationRequest();
        registrationRequest2.setFirstName("Joel");
        registrationRequest2.setLastName("Chukwu");
        registrationRequest2.setHouseNumber("21");
        registrationRequest2.setStreetName("Sapele road");
        registrationRequest2.setClosestLandMark("Akugha junction");
        registrationRequest2.setState("Abuja");
        registrationRequest2.setPhoneNumber("07033099619");
        registrationRequest2.setEmail("johnlegend@gmail.com");
        registrationRequest2.setUsername("username");
        registrationRequest2.setPassword("password");
        assertThrows(EmailAndUsernameTakenException.class, ()-> customerService.registerCustomer(registrationRequest2));
    }

    @Test
    public void testThatRegisteredUserCanLogInWhenUsernameAndPasswordAreCorrect() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);
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
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant.getEmail());
        loginRequest1.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant.isLocked());


        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(BigDecimal.valueOf(500.00));
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu = new Menu();
        userSelectedMenu.setItemName(menuRequest.getItemName());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenu(userSelectedMenu);
        orderItem1.setQuantity(1);

        orderRequest.setItems(List.of(orderItem1));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse placeOrderResponse = orderService.createOrder(orderRequest);

        assertNotNull(placeOrderResponse);
        assertThat(orderService.count(), is(1L));

        notificationService.sendNotification(orderRequest);
        assertThat(notificationService.count(), is(1L));

    }

    @Test
    public void testThatOrderCanBeCanceled() {
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant.getEmail());
        loginRequest1.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant.isLocked());


        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(BigDecimal.valueOf(500.00));
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu = new Menu();
        userSelectedMenu.setItemName(menuRequest.getItemName());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenu(userSelectedMenu);
        orderItem1.setQuantity(1);

        orderRequest.setItems(List.of(orderItem1));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse placeOrderResponse = orderService.createOrder(orderRequest);

        assertNotNull(placeOrderResponse);
        assertThat(orderService.count(), is(1L));

        notificationService.sendNotification(orderRequest);
        assertThat(notificationService.count(), is(1L));

        Order order = customerService.cancelOrder(orderRequest);

        assertThat(order.getStatus(), is(Status.CANCELED));
    }


    @Test
    public void testThatExceptionIsThrownWhen_UpdateAttemptIsMade_ButOrderNotFound() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant.getEmail());
        loginRequest1.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant.isLocked());

        Restaurant restaurant2 = registerRestaurant("Dolphins", "dolphns@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(2L));

        restaurant2  = restaurantService.setLocked(restaurant2.getName());
        assertTrue(restaurant2.isLocked());

        RestaurantLoginRequest loginRequest2 = new RestaurantLoginRequest();
        loginRequest2.setEmail(restaurant2.getEmail());
        loginRequest2.setPassword(restaurant2.getPassword());

        restaurant2 = restaurantService.unlock(loginRequest2);
        assertFalse(restaurant2.isLocked());

        Restaurant restaurant3 = registerRestaurant("Ng's Cuisine", "ngcuisine@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(3L));

        restaurant3  = restaurantService.setLocked(restaurant3.getName());
        assertTrue(restaurant3.isLocked());

        RestaurantLoginRequest loginRequest3 = new RestaurantLoginRequest();
        loginRequest3.setEmail(restaurant3.getEmail());
        loginRequest3.setPassword(restaurant3.getPassword());

        restaurant3 = restaurantService.unlock(loginRequest3);
        assertFalse(restaurant3.isLocked());

        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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

        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(BigDecimal.valueOf(500.00));
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CreateMenuRequest menuRequested = new CreateMenuRequest();
        menuRequested.setRestaurant(restaurant2.getName());
        menuRequested.setItemName("Abacha n'akpaka");
        menuRequested.setPrice(BigDecimal.valueOf(500.00));
        menuRequested.setAvailability(true);

        CreateMenuResponse menuResponses = menuService.createMenu(menuRequested);

        assertNotNull(menuResponses);

        CreateMenuRequest menuRequest1 = new CreateMenuRequest();
        menuRequest1.setRestaurant(restaurant3.getName());
        menuRequest1.setItemName("Rice & Beans");
        menuRequest1.setPrice(BigDecimal.valueOf(500.00));
        menuRequest1.setAvailability(true);

        CreateMenuResponse menuResponse1 = menuService.createMenu(menuRequest1);

        assertNotNull(menuResponse1);

        CreateMenuRequest menuRequest2 = new CreateMenuRequest();
        menuRequest2.setRestaurant(restaurant3.getName());
        menuRequest2.setItemName("Semo & Onugbu");
        menuRequest2.setPrice(BigDecimal.valueOf(500.00));
        menuRequest2.setAvailability(true);

        CreateMenuResponse menuResponse2 = menuService.createMenu(menuRequest2);
        assertNotNull(menuResponse2);

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu = new Menu();
        userSelectedMenu.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu2 = new Menu();
        userSelectedMenu2.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu3 = new Menu();
        userSelectedMenu3.setItemName(menuRequest2.getItemName());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenu(userSelectedMenu);
        orderItem1.setQuantity(1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setMenu(userSelectedMenu2);
        orderItem2.setQuantity(1);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setMenu(userSelectedMenu3);
        orderItem3.setQuantity(1);

        orderRequest.setItems(List.of(orderItem1, orderItem2, orderItem3));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderRequest orderRequest1 = new PlaceOrderRequest();
        orderRequest1.setUsername(customer.getUsername());
        orderRequest1.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu4 = new Menu();
        userSelectedMenu4.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu5 = new Menu();
        userSelectedMenu5.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu6 = new Menu();
        userSelectedMenu6.setItemName(menuRequest2.getItemName());

        OrderItem orderItem4 = new OrderItem();
        orderItem4.setMenu(userSelectedMenu4);
        orderItem4.setQuantity(2);

        OrderItem orderItem5 = new OrderItem();
        orderItem5.setMenu(userSelectedMenu5);
        orderItem5.setQuantity(2);

        OrderItem orderItem6 = new OrderItem();
        orderItem6.setMenu(userSelectedMenu6);
        orderItem6.setQuantity(2);

        orderRequest1.setItems(List.of(orderItem4, orderItem5, orderItem6));
        orderRequest1.setDriver("Jake");
        orderRequest1.setStatus(Status.AWAITING_DELIVERY);
        orderRequest1.setDriverPhone("07023536754");
        orderRequest1.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse placeOrderResponse = orderService.createOrder(orderRequest);
        PlaceOrderResponse placeOrderResponse1 = orderService.createOrder(orderRequest1);

        assertNotNull(placeOrderResponse);
        assertNotNull(placeOrderResponse1);
        assertThat(orderService.count(), is(2L));

        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
        updateOrderRequest.setName(customer.getUsername());
        updateOrderRequest.setStatus(Status.DELIVERED);
        updateOrderRequest.setTimeStamp(LocalDateTime.now());

        Order order = customerService.updateOrder(updateOrderRequest);
        assertThat(order.getStatus(), is(Status.DELIVERED));

        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setCustomer(registrationRequest.getUsername());
        feedbackRequest.setRestaurant(orderRequest.getRestaurants());
        feedbackRequest.setDriver(orderRequest.getDriver());
        feedbackRequest.setRating(10);
        feedbackRequest.setComment("Timely delivery plus tasty food! Awesome experience");
        FeedbackResponse response1 = customerService.provideFeedback(feedbackRequest);

        assertNotNull(response1);
        assertThat(feedbackService.count(), is(1L));
    }

    @Test
    public void testThatCustomerCanFindAllOrderHistory() {
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant.getEmail());
        loginRequest1.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant.isLocked());

        Restaurant restaurant2 = registerRestaurant("Dolphins", "dolphns@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(2L));

        restaurant2  = restaurantService.setLocked(restaurant2.getName());
        assertTrue(restaurant2.isLocked());

        RestaurantLoginRequest loginRequest2 = new RestaurantLoginRequest();
        loginRequest2.setEmail(restaurant2.getEmail());
        loginRequest2.setPassword(restaurant2.getPassword());

        restaurant2 = restaurantService.unlock(loginRequest2);
        assertFalse(restaurant2.isLocked());

        Restaurant restaurant3 = registerRestaurant("Ng's Cuisine", "ngcuisine@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(3L));

        restaurant3  = restaurantService.setLocked(restaurant3.getName());
        assertTrue(restaurant3.isLocked());

        RestaurantLoginRequest loginRequest3 = new RestaurantLoginRequest();
        loginRequest3.setEmail(restaurant3.getEmail());
        loginRequest3.setPassword(restaurant3.getPassword());

        restaurant3 = restaurantService.unlock(loginRequest3);
        assertFalse(restaurant3.isLocked());

        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
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

        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(BigDecimal.valueOf(500.00));
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CreateMenuRequest menuRequested = new CreateMenuRequest();
        menuRequested.setRestaurant(restaurant2.getName());
        menuRequested.setItemName("Abacha n'akpaka");
        menuRequested.setPrice(BigDecimal.valueOf(500.00));
        menuRequested.setAvailability(true);

        CreateMenuResponse menuResponses = menuService.createMenu(menuRequested);

        assertNotNull(menuResponses);

        CreateMenuRequest menuRequest1 = new CreateMenuRequest();
        menuRequest1.setRestaurant(restaurant3.getName());
        menuRequest1.setItemName("Rice & Beans");
        menuRequest1.setPrice(BigDecimal.valueOf(500.00));
        menuRequest1.setAvailability(true);

        CreateMenuResponse menuResponse1 = menuService.createMenu(menuRequest1);

        assertNotNull(menuResponse1);

        CreateMenuRequest menuRequest2 = new CreateMenuRequest();
        menuRequest2.setRestaurant(restaurant3.getName());
        menuRequest2.setItemName("Semo & Onugbu");
        menuRequest2.setPrice(BigDecimal.valueOf(500.00));
        menuRequest2.setAvailability(true);

        CreateMenuResponse menuResponse2 = menuService.createMenu(menuRequest2);

        assertNotNull(menuResponse2);

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu = new Menu();
        userSelectedMenu.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu2 = new Menu();
        userSelectedMenu2.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu3 = new Menu();
        userSelectedMenu3.setItemName(menuRequest2.getItemName());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenu(userSelectedMenu);
        orderItem1.setQuantity(1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setMenu(userSelectedMenu2);
        orderItem2.setQuantity(1);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setMenu(userSelectedMenu3);
        orderItem3.setQuantity(1);

        orderRequest.setItems(List.of(orderItem1, orderItem2, orderItem3));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderRequest orderRequest1 = new PlaceOrderRequest();
        orderRequest1.setUsername(customer.getUsername());
        orderRequest1.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu4 = new Menu();
        userSelectedMenu4.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu5 = new Menu();
        userSelectedMenu5.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu6 = new Menu();
        userSelectedMenu6.setItemName(menuRequest2.getItemName());

        OrderItem orderItem4 = new OrderItem();
        orderItem4.setMenu(userSelectedMenu4);
        orderItem4.setQuantity(2);

        OrderItem orderItem5 = new OrderItem();
        orderItem5.setMenu(userSelectedMenu5);
        orderItem5.setQuantity(2);

        OrderItem orderItem6 = new OrderItem();
        orderItem6.setMenu(userSelectedMenu6);
        orderItem6.setQuantity(2);

        orderRequest1.setItems(List.of(orderItem4, orderItem5, orderItem6));
        orderRequest1.setDriver("Jake");
        orderRequest1.setStatus(Status.AWAITING_DELIVERY);
        orderRequest1.setDriverPhone("07023536754");
        orderRequest1.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse placeOrderResponse = orderService.createOrder(orderRequest);
        PlaceOrderResponse placeOrderResponse1 = orderService.createOrder(orderRequest1);

        assertNotNull(placeOrderResponse);
        assertNotNull(placeOrderResponse1);
        assertThat(orderService.count(), is(2L));

//        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();
//        updateOrderRequest.setName(registrationRequest.getUsername());
//        updateOrderRequest.setStatus(Status.DELIVERED);
//        updateOrderRequest.setTimeStamp(LocalDateTime.now());
//
//        Order order = customerService.updateOrder(updateOrderRequest);
//        assertThat(order.getStatus(), is(Status.DELIVERED));

        List<Order> allOrders = customerService.findAllTransactionHistory();

        assertNotNull(allOrders);
//        assertThat(allOrders).isNotNull();
    }

    @Test
    public void testThatOrderTotalCanBeCalculated() {
        Restaurant restaurant = registerRestaurant("Ntachi osa", "ntachiosa@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(1L));

        restaurant  = restaurantService.setLocked(restaurant.getName());
        assertTrue(restaurant.isLocked());

        RestaurantLoginRequest loginRequest1 = new RestaurantLoginRequest();
        loginRequest1.setEmail(restaurant.getEmail());
        loginRequest1.setPassword(restaurant.getPassword());

        restaurant = restaurantService.unlock(loginRequest1);
        assertFalse(restaurant.isLocked());

        Restaurant restaurant2 = registerRestaurant("Dolphins", "dolphns@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(2L));

        restaurant2  = restaurantService.setLocked(restaurant2.getName());
        assertTrue(restaurant2.isLocked());

        RestaurantLoginRequest loginRequest2 = new RestaurantLoginRequest();
        loginRequest2.setEmail(restaurant2.getEmail());
        loginRequest2.setPassword(restaurant2.getPassword());

        restaurant2 = restaurantService.unlock(loginRequest2);
        assertFalse(restaurant2.isLocked());

        Restaurant restaurant3 = registerRestaurant("Ng's Cuisine", "ngcuisine@gmail.com", "passworded");
        assertThat(restaurantService.count(), is(3L));

        restaurant3  = restaurantService.setLocked(restaurant3.getName());
        assertTrue(restaurant3.isLocked());

        RestaurantLoginRequest loginRequest3 = new RestaurantLoginRequest();
        loginRequest3.setEmail(restaurant3.getEmail());
        loginRequest3.setPassword(restaurant3.getPassword());

        restaurant3 = restaurantService.unlock(loginRequest3);
        assertFalse(restaurant3.isLocked());

        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        registrationRequest.setFirstName("Joel");
        registrationRequest.setLastName("Chukwu");
        registrationRequest.setHouseNumber("21");
        registrationRequest.setStreetName("Sapele road");
        registrationRequest.setClosestLandMark("Akugha junction");
        registrationRequest.setState("Abuja");
        registrationRequest.setPhoneNumber("07033099619");
        registrationRequest.setEmail("johnlegend@gmail.com");
        registrationRequest.setUsername("username");
        registrationRequest.setPassword("password");
        customerService.registerCustomer(registrationRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        Customer customer = customerService.unlock(loginRequest);
        assertFalse(customer.isLocked());

        // @TODO: list the food from a particular restaurant

        CreateMenuRequest menuRequest = new CreateMenuRequest();
        menuRequest.setRestaurant(restaurant.getName());
        menuRequest.setItemName("Rice");
        menuRequest.setPrice(BigDecimal.valueOf(500.00));
        menuRequest.setAvailability(true);

        CreateMenuResponse menuResponse = menuService.createMenu(menuRequest);

        assertNotNull(menuResponse);

        CreateMenuRequest menuRequested = new CreateMenuRequest();
        menuRequested.setRestaurant(restaurant2.getName());
        menuRequested.setItemName("Abacha n'akpaka");
        menuRequested.setPrice(BigDecimal.valueOf(500.00));
        menuRequested.setAvailability(true);

        CreateMenuResponse menuResponses = menuService.createMenu(menuRequested);

        assertNotNull(menuResponses);

        CreateMenuRequest menuRequest1 = new CreateMenuRequest();
        menuRequest1.setRestaurant(restaurant3.getName());
        menuRequest1.setItemName("Rice & Beans");
        menuRequest1.setPrice(BigDecimal.valueOf(500.00));
        menuRequest1.setAvailability(true);

        CreateMenuResponse menuResponse1 = menuService.createMenu(menuRequest1);

        assertNotNull(menuResponse1);

        CreateMenuRequest menuRequest2 = new CreateMenuRequest();
        menuRequest2.setRestaurant(restaurant3.getName());
        menuRequest2.setItemName("Semo & Onugbu");
        menuRequest2.setPrice(BigDecimal.valueOf(500.00));
        menuRequest2.setAvailability(true);

        CreateMenuResponse menuResponse2 = menuService.createMenu(menuRequest2);

        assertNotNull(menuResponse2);

        assertThat(menuService.count(), is(4L));

        // @TODO: place order containing a food from the restaurant menu
        // @TODO: set the restaurant we are ordering from

        PlaceOrderRequest orderRequest = new PlaceOrderRequest();
        orderRequest.setUsername(customer.getUsername());
        orderRequest.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu = new Menu();
        userSelectedMenu.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu2 = new Menu();
        userSelectedMenu2.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu3 = new Menu();
        userSelectedMenu3.setItemName(menuRequest2.getItemName());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenu(userSelectedMenu);
        orderItem1.setQuantity(1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setMenu(userSelectedMenu2);
        orderItem2.setQuantity(1);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setMenu(userSelectedMenu3);
        orderItem3.setQuantity(1);

        orderRequest.setItems(List.of(orderItem1, orderItem2, orderItem3));
        orderRequest.setDriver("Jake");
        orderRequest.setStatus(Status.AWAITING_DELIVERY);
        orderRequest.setDriverPhone("07023536754");
        orderRequest.setTimeStamp(LocalDateTime.now());

        PlaceOrderRequest orderRequest1 = new PlaceOrderRequest();
        orderRequest1.setUsername(customer.getUsername());
        orderRequest1.setRestaurants(List.of(restaurant));

        Menu userSelectedMenu4 = new Menu();
        userSelectedMenu4.setItemName(menuRequest.getItemName());

        Menu userSelectedMenu5 = new Menu();
        userSelectedMenu5.setItemName(menuRequest1.getItemName());

        Menu userSelectedMenu6 = new Menu();
        userSelectedMenu6.setItemName(menuRequest2.getItemName());

        OrderItem orderItem4 = new OrderItem();
        orderItem4.setMenu(userSelectedMenu4);
        orderItem4.setQuantity(2);

        OrderItem orderItem5 = new OrderItem();
        orderItem5.setMenu(userSelectedMenu5);
        orderItem5.setQuantity(2);

        OrderItem orderItem6 = new OrderItem();
        orderItem6.setMenu(userSelectedMenu6);
        orderItem6.setQuantity(2);

        orderRequest1.setItems(List.of(orderItem4, orderItem5, orderItem6));
        orderRequest1.setDriver("Jake");
        orderRequest1.setStatus(Status.AWAITING_DELIVERY);
        orderRequest1.setDriverPhone("07023536754");
        orderRequest1.setTimeStamp(LocalDateTime.now());

        PlaceOrderResponse placeOrderResponse = orderService.createOrder(orderRequest);
        PlaceOrderResponse placeOrderResponse1 = orderService.createOrder(orderRequest1);

        assertNotNull(placeOrderResponse);
        assertNotNull(placeOrderResponse1);
        assertThat(orderService.count(), is(2L));
        assertThat(orderRequest.getStatus(), is(Status.AWAITING_DELIVERY));

        List<PlaceOrderRequest> orderRequests = List.of(orderRequest, orderRequest1);

        double total = orderService.calculateTotal(orderRequests);

        assertEquals(1500.0, total, 0.001);

        assertEquals(1500.0, orderService.calculateTotal(orderRequests));
    }

    private Restaurant registerRestaurant(String name, String email, String password) {
        RestaurantRegRequest regRequest = new RestaurantRegRequest();
        regRequest.setName(name);
        regRequest.setEmail(email);
        regRequest.setPassword(password);
        restaurantService.registerRestaurant(regRequest);
        return restaurantService.findByName(name).orElseThrow();
    }
}