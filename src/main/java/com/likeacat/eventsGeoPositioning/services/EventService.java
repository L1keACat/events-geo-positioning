package com.likeacat.eventsGeoPositioning.services;

import com.likeacat.eventsGeoPositioning.DAO.EventDAO;
import com.likeacat.eventsGeoPositioning.DAO.SequenceDAO;
import com.likeacat.eventsGeoPositioning.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired private SequenceDAO sequenceDao;
    @Autowired private EventDAO eventDAO;

    public void add(Event event) {
        event.setId(sequenceDao.getNextSequenceId(Event.COLLECTION_NAME));
        eventDAO.save(event);
    }

    public void update(Event event) {
        eventDAO.save(event);
    }

    public Event get(Long id) {
        return eventDAO.get(id);
    }

    public List<Event> getAll() {
        return eventDAO.getAll();
    }

    public void remove(Long id) {
        eventDAO.remove(id);
    }
}
