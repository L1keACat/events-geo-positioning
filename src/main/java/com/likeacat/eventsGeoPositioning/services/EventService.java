package com.likeacat.eventsGeoPositioning.services;

import com.likeacat.eventsGeoPositioning.DAO.SequenceDAO;
import com.likeacat.eventsGeoPositioning.model.Event;
import com.likeacat.eventsGeoPositioning.repository.EventRepository;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EventService {
    @Autowired private SequenceDAO sequenceDao;
    @Autowired private EventRepository eventRepository;

    public void add(Event event) {
        event.setId(sequenceDao.getNextSequenceId(Event.COLLECTION_NAME));
        eventRepository.save(event);
    }

    public void update(Event event) {
        eventRepository.save(event);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public void remove(Long id) {
        Event event = eventRepository.findById(id);
        eventRepository.delete(event);
    }

    public void random_gen() throws IOException, JSONException {
        String coord, coord2;
        String apikey = "e7c0fa9f-f120-48ee-993c-934d78d54607";
        //double lng = 27.561481;
        //double lat = 53.902496;
        double rand_lat, rand_lng;
        int t;
        String[] events = {"Выставка", "Велопробег", "Забег", "Хакатон", "Дрифт",
                "Гонки", "Семинар", "Хакатон", "Концерт", "Авария", "Парад", "Салют"};

        for (int i = 0; i < 10; i++) {
            rand_lat = 53.84 + (Math.random() * 0.12); //53.84 53.96
            rand_lng = 27.43 + (Math.random() * 0.24); //27.43 27.67
            t = (int) (Math.random() * 12);

            coord = rand_lng + "," + rand_lat;
            coord2 = rand_lat + "," + rand_lng;

            String requestUrl = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=" + apikey + "&geocode=" + coord;

            JSONObject json = new JSONObject(IOUtils.toString(new URL(requestUrl), StandardCharsets.UTF_8));
            JSONObject response = (JSONObject) json.get("response");
            JSONObject GeoObjectCollection = (JSONObject) response.get("GeoObjectCollection");
            JSONArray featureMember = (JSONArray) GeoObjectCollection.get("featureMember");
            JSONObject member = featureMember.getJSONObject(0);
            JSONObject GeoObject = (JSONObject) member.get("GeoObject");
            String address = (String) GeoObject.get("name");

            Event event = new Event(events[t], address, coord2);
            event.setId(sequenceDao.getNextSequenceId(Event.COLLECTION_NAME));
            eventRepository.save(event);
        }
    }
}
