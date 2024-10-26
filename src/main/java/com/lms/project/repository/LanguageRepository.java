package com.lms.project.repository;

import com.lms.project.entity1.Language.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface LanguageRepository extends MongoRepository<Language,String> {
    @Query("{'LanguageName':?0}")
     Optional<Object> findByLanguageName(String LanguageName);

}
