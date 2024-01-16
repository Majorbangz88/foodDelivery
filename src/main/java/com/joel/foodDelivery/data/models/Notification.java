package com.joel.foodDelivery.data.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {

    private String sender;
    private String message;
    private String menu;
    private String driver;
    private String restaurant;
    private Status status;
    private LocalDateTime dateTime;

}
