package com.lms.project.repository;

import com.lms.project.entity1.RegisterUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegisterRepository extends MongoRepository<RegisterUser,String> {
    Optional<Object> findByMobileNo(String mobileNo);
}
