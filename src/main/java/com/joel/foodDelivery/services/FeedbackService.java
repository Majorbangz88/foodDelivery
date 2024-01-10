package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.FeedbackRequest;
import com.joel.foodDelivery.dtos.requests.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse provideFeedback(FeedbackRequest feedbackRequest);

    Long count();

    void deleteAll();
}
