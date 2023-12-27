package com.joel.foodDelivery.data.repositories;

import com.joel.foodDelivery.data.models.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
