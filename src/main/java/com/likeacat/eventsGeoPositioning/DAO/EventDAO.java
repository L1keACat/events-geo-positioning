package com.likeacat.eventsGeoPositioning.DAO;

import com.likeacat.eventsGeoPositioning.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDAO {
    @Autowired private MongoOperations mongoOperations;

    public void save(Event event) {
        mongoOperations.save(event);
    }

    public Event get(Long id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), Event.class);
    }

    public List<Event> getAll() {
        return mongoOperations.findAll(Event.class);
    }

    public void remove(Long id) {
        mongoOperations.remove(Query.query(Criteria.where("id").is(id)), Event.class);
    }
}
