package com.likeacat.eventsGeoPositioning.repository;


import com.likeacat.eventsGeoPositioning.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
