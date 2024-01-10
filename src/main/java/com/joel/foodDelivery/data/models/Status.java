package com.joel.foodDelivery.data.models;

import lombok.Getter;

@Getter
public enum Status {

    AVAILABLE,
    UNAVAILABLE,
    CANCELED,
    DELIVERED,
    AWAITING_DELIVERY,
    OLD,
    NEW;
}
