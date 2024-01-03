package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.data.repositories.OrderRepository;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import com.joel.foodDelivery.dtos.requests.UpdateOrderRequest;
import com.joel.foodDelivery.exceptions.OrderNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Override
    public Order createOrder(PlaceOrderRequest orderRequest) {
        Order newOrder = new Order();
        newOrder.setCustomer(orderRequest.getCustomer());
        newOrder.setRestaurant(orderRequest.getRestaurant());
        newOrder.setDriver(orderRequest.getDriver());
        newOrder.setTimeStamp(orderRequest.getTimeStamp());
        newOrder.setMenu(orderRequest.getMenu());
        newOrder.setStatus(orderRequest.getStatus());
        orderRepository.save(newOrder);

        return newOrder;
    }

    @Override
    public Order updateOrder(UpdateOrderRequest updateOrder) {
        Optional<Order> order = orderRepository.findByCustomer(updateOrder.getCustomer());
        if (order.isEmpty()) throw new OrderNotExistException("Order not found");
        if (order.get().getCustomer().equals(updateOrder.getCustomer())
                && order.get().getRestaurant().equals(updateOrder.getRestaurant())
                && order.get().getDriver().equals(updateOrder.getDriver()))
            orderRepository.save(order.get());

        return order.get();
    }
}
