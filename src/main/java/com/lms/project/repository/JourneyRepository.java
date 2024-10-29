package com.lms.project.repository;

import com.lms.project.entity1.Journey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface JourneyRepository extends MongoRepository<Journey,String> {
    @Query("{'journeyName':?0}")
    Optional<Object> findByName(String journeyName);
}
