package com.likeacat.eventsGeoPositioning.repository;

import com.likeacat.eventsGeoPositioning.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
