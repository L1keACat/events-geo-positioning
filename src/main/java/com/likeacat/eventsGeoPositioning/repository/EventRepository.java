package com.likeacat.eventsGeoPositioning.repository;

import com.likeacat.eventsGeoPositioning.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

    Event findById(Long id);
}
