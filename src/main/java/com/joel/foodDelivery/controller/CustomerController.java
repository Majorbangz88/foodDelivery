package com.joel.foodDelivery.controller;

import com.joel.foodDelivery.data.models.Status;
import com.joel.foodDelivery.dtos.requests.*;
import com.joel.foodDelivery.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "foodDeliveryApp")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("api/vi/registerCustomer")
    public String customerRegister(@RequestBody CustomerRegistrationRequest registrationRequest) {
        try{
            customerService.registerCustomer(registrationRequest);
            return "Registration successful";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping(path = "api/vi/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            customerService.unlock(loginRequest);
            return "Login successful";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping(path = "api/vi/logout")
    public String logout(@RequestBody String username) {
        try {
            customerService.setLock(username);
            return "See you soonest " + username;
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping(path = "api/v1/createOrder")
    public String createOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        try {
            customerService.createOrder(placeOrderRequest);
            return "Thank you! Your order has been successfully placed";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PutMapping(path = "api/v1/updateOrder")
    public String updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest) {
        try {
            customerService.updateOrder(updateOrderRequest);
            return "Your order has been updated successfully";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PatchMapping(path = "api/v1/cancelOrder")
    public String cancelOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        try {
            customerService.cancelOrder(placeOrderRequest);
            return "Order successfully canceled";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping(path = "api/v1/provideFeedback")
    public String giveFeedBack(@RequestBody FeedbackRequest feedbackRequest) {
        try {
            customerService.provideFeedback(feedbackRequest);
            return "Feedback sent";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @GetMapping(path = "api/v1/orderHistory")
    public String orderHistory() {
        try {
            customerService.findAllTransactionHistory();
            return "Here's your order history";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
