package com.lms.project.repository;
import com.lms.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends MongoRepository<User,String> {
    @Query("{'mobileNo:?0'}")
    Optional<User> findByEmailOrMobileNo(String userEmailID, String mobile);
    @Query("{'email':?0}")
    Optional<User> findByEmail(String user);
    @Query("{'userName':?0}")
    List<User> findByUserName(String search);
    @Query("{}")
    Page<User> getAll(Pageable pageable);
    @Query("{'userName': { $regex: ?0, $options: 'i' }}")
    Page<User> findByUsername(String search, Pageable pageable);
}
