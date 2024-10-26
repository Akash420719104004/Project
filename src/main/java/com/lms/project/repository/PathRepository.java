package com.lms.project.repository;

import com.lms.project.entity1.Path.Path;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PathRepository extends MongoRepository<Path,String> {

    Optional<Object> findByPathName(String pathName);
}
