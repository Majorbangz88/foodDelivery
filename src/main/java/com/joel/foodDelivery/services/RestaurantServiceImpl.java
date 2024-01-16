package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.data.models.Restaurant;
import com.joel.foodDelivery.data.repositories.RestaurantRepository;
import com.joel.foodDelivery.dtos.requests.*;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import com.joel.foodDelivery.exceptions.IncorrectUsernameOrPasswordException;
import com.joel.foodDelivery.exceptions.InvalidNameOrPasswordException;
import com.joel.foodDelivery.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.joel.foodDelivery.java.utils.Mapper.map;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private OrderService orderService;

    @Override
    public RestaurantRegResponse registerRestaurant(RestaurantRegRequest regRequest) {
//        validateNotNullNameOrPassword(regRequest);
        validateNameAndEmail(regRequest);
        Restaurant restaurant = map(regRequest);
        restaurantRepository.save(restaurant);

        RestaurantRegResponse response = new RestaurantRegResponse();
        response.setRestaurantId(restaurant.getId());

        return response;
    }

    private void validateNotNullNameOrPassword(RestaurantRegRequest regRequest) {
        boolean isNullName = regRequest.getName() == null;
        boolean isNullPassword = regRequest.getPassword() == null;
        if (isNullName || isNullPassword)
            throw new InvalidNameOrPasswordException("Invalid name or password");
    }

    private void validateNameAndEmail(RestaurantRegRequest regRequest) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByNameAndPassword(
                regRequest.getName(), regRequest.getPassword());
        if (optionalRestaurant.isPresent())
            throw new EmailAndUsernameTakenException("Details Already exist");
    }

    @Override
    public Long count() {
        return restaurantRepository.count();
    }

    @Override
    public void deleteAll() {
        restaurantRepository.deleteAll();
    }

    @Override
    public Restaurant setLocked(String name) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(name);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setLocked(true);
            restaurantRepository.save(restaurant);
            return restaurant;
        }
        return null;
    }


    @Override
    public Optional<Restaurant> findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Restaurant unlock(RestaurantLoginRequest loginRequest) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByEmail(loginRequest.getEmail());
        if (optionalRestaurant.isEmpty())
            throw new UserNotFoundException("This user was not found");
        if (optionalRestaurant.get().getEmail().equals(loginRequest.getEmail())
                && optionalRestaurant.get().getPassword().equals(loginRequest.getPassword()))
            optionalRestaurant.get().setLocked(false);
        else throw new IncorrectUsernameOrPasswordException("Name or password incorrect");
        restaurantRepository.save(optionalRestaurant.get());

        return optionalRestaurant.get();
    }

//    @Override
//    public Order receiveOrder(PlaceOrderRequest placeOrderRequest) {
//        Optional<Order> optionalOrder = orderService.findByCustomer(placeOrderRequest.getCustomer());
//        if (optionalOrder.isPresent()) {
//            Order order = optionalOrder.get();
//
//            if (!order.getStatus().equalsIgnoreCase("Received")) {
//                order.setStatus("Received");
//                orderService.save(order);
//
//                Restaurant restaurant = order.getRestaurant();
//                restaurant.getOrders().add(order);
//                restaurantRepository.save(restaurant);
//
//                return order;
//            } else {
//                throw new OrderAlreadyReceivedException("Order has already been received.");
//            }
//        } else {
//            throw new OrderNotFoundException("Order not found.");
//        }
//    }

}
