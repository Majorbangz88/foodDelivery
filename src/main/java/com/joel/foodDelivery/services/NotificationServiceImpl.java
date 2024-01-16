package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Notification;
import com.joel.foodDelivery.data.models.Status;
import com.joel.foodDelivery.data.repositories.NotificationRepository;
import com.joel.foodDelivery.dtos.requests.PlaceOrderRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

//    @Autowired
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    @Override
    public Notification sendNotification(PlaceOrderRequest request) {
        Notification notification = new Notification();
        notification.setSender(request.getUsername());
        notification.setRestaurant(request.getRestaurantName());
        notification.setMenu(request.getMenu());
        notification.setDriver(request.getDriver());
        notification.setStatus(Status.NEW);
        notification.setDateTime(request.getTimeStamp());

        notificationRepository.save(notification);

        return notification;
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }

    public List<Notification> receiveNotification(String restaurantName) {
        List<Notification> notifications = notificationRepository.findByRestaurantAndStatus(restaurantName, Status.NEW);

        for (Notification notification: notifications) {
            notification.setStatus(Status.OLD);
            notificationRepository.save(notification);
        }
        return notifications;
    }
}
