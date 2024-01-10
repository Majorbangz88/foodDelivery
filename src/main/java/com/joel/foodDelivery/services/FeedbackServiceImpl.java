package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Feedback;
import com.joel.foodDelivery.data.repositories.FeedbackRepository;
import com.joel.foodDelivery.dtos.requests.FeedbackRequest;
import com.joel.foodDelivery.dtos.requests.FeedbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Override
    public FeedbackResponse provideFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        feedback.setCustomer(feedbackRequest.getCustomer());
        feedback.setRestaurant(feedbackRequest.getRestaurant());
        feedback.setDriver(feedbackRequest.getDriver());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setComment(feedbackRequest.getComment());
        feedbackRepository.save(feedback);

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setCustomer(feedbackResponse.getCustomer());
        feedbackResponse.setRestaurant(feedbackResponse.getRestaurant());
        feedbackResponse.setDriver(feedbackResponse.getDriver());
        feedbackResponse.setRating(feedbackResponse.getRating());
        feedbackResponse.setComment(feedbackResponse.getComment());

        return feedbackResponse;
    }

    @Override
    public Long count() {
        return feedbackRepository.count();
    }

    @Override
    public void deleteAll() {
        feedbackRepository.deleteAll();;
    }
}
