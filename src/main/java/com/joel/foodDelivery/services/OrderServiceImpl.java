package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.*;
import com.joel.foodDelivery.data.repositories.OrderRepository;
import com.joel.foodDelivery.dtos.requests.*;
import com.joel.foodDelivery.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DriverService driverService;

//    public void setup(CustomerService customerService,
//                      MenuService menuService,
//                      RestaurantService restaurantService,
//                      DriverService driverService){
//        this.customerService = customerService;
//        this.menuService = menuService;
//        this.restaurantService = restaurantService;
//        this.driverService = driverService;
//    }
    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Override
    public PlaceOrderResponse createOrder(PlaceOrderRequest orderRequest) {
        Customer customer = getCustomer(orderRequest.getUsername());
        List<OrderItem> allMenuOrders = orderRequest.getItems();
        List<Menu> allMenu = new ArrayList<>();
        allMenuOrders.forEach((order) -> {
            Menu menu = getMenu(order);
            allMenu.add(menu);
        });

        List<Restaurant> restaurants = orderRequest.getRestaurants();
        Driver driver = getDriver(orderRequest.getDriverPhone());

        Order newOrder = new Order();

        newOrder.setCustomerName(customer.getUsername());
        newOrder.setEmail(customer.getEmail());
        newOrder.setRestaurants(restaurants);
//        newOrder.setRestaurantName(restaurant.getName());
        newOrder.setDriver(driver);
        newOrder.setMenu(allMenu);
        newOrder.setTimeStamp(orderRequest.getTimeStamp());
        newOrder.setStatus(orderRequest.getStatus());

        orderRepository.save(newOrder);

        PlaceOrderResponse response = new PlaceOrderResponse();
        response.setRestaurants(orderRequest.getRestaurants());
        response.setItems(orderRequest.getItems());
        response.setUsername(orderRequest.getUsername());
        response.setStatus(orderRequest.getStatus());
        response.setDriver(newOrder.getDriver().getName());
        response.setDriverPhone(newOrder.getDriver().getPhoneNumber());
        response.setTimeStamp(newOrder.getTimeStamp());

        return response;
    }

    private Driver getDriver(String phoneNumber) {
        Optional<Driver> optionalDriver = driverService.findByPhoneNumber(phoneNumber);
        if (optionalDriver.isPresent()) {
            return optionalDriver.get();
        }
        else {
            throw new DriverNotFoundException("This driver does not exist");
        }
    }

//    private List<Restaurant> getRestaurants(List<Restaurant> restaurants) throws RestaurantNotFoundException {
//        Optional<List<Restaurant>> optionalRestaurants = restaurantService.findByName(restaurants.);
//        if (optionalRestaurants.isPresent())
//            return optionalRestaurants.get();
//        else throw new RestaurantNotFoundException("Restaurant does not exist");
//    }

    private Menu getMenu(OrderItem items) {
        Optional<Menu> optionalMenu = menuService.findByname(items.getMenu().getItemName());
        if ((optionalMenu.isPresent()))
            return optionalMenu.get();
        else throw new MenuNotFoundException("Menu not found");
    }

    private Customer getCustomer(String username) {
        Optional<Customer> optionalCustomer = customerService.findByUsername(username);
        if (optionalCustomer.isPresent())
            return optionalCustomer.get();
        else throw new CustomerNotfoundException("This customer exists not");
    }

    @Override
    public Order updateOrder(UpdateOrderRequest updateOrder) {
        Optional<Order> optionalOrder = orderRepository.findOrderByCustomerName(updateOrder.getName());
        if (optionalOrder.isEmpty())
            throw new OrderNotExistException("Order not found");
        optionalOrder.get().setStatus(updateOrder.getStatus());
        optionalOrder.get().setTimeStamp(LocalDateTime.now());
        orderRepository.save(optionalOrder.get());
        UpdateOrderResponse response = new UpdateOrderResponse();
        response.setStatus(response.getStatus());
        response.setDateTime(updateOrder.getTimeStamp());
        return optionalOrder.get();
    }
//
//    @Override
//    public String trackOrderStatus(String orderId) {
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//        if (optionalOrder.isPresent()) {
//            Order order = optionalOrder.get();
//            return order;
//        }
//        else throw new OrderNotExistException("Order with " + orderId + " not found");
//    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(PlaceOrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findOrderByCustomerName(orderRequest.getUsername());
        if (optionalOrder.isEmpty())
            throw new OrderNotExistException("Order not found");
        optionalOrder.get().setStatus(Status.CANCELED);
        orderRepository.save(optionalOrder.get());
//        orderRepository.delete(optionalOrder.get());
        return optionalOrder.get();
    }

    @Override
    public List<Order> displayOrderHistory() {
        return orderRepository.findAll();
    }

    @Override
    public double calculateTotal(List<PlaceOrderRequest> orderRequests) {
        double total = 0.0;

        for (PlaceOrderRequest orderRequest : orderRequests) {
            List<OrderItem> items = orderRequest.getItems();

            for (OrderItem orderItem : items) {
                BigDecimal price = orderItem.getMenu().getPrice();
                int quantity = orderItem.getQuantity();

                double result = price.doubleValue() * quantity;

                total += result;
            }
        }

        return total;
    }


}
