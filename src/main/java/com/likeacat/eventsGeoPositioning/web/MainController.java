package com.likeacat.eventsGeoPositioning.web;

import com.likeacat.eventsGeoPositioning.model.Event;
import com.likeacat.eventsGeoPositioning.services.EventService;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Controller
public class MainController {
    @Autowired
    private EventService eventService;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/","/all"}, method = RequestMethod.GET)
    public String showAll(Model model) {

        model.addAttribute("events", eventService.getAll());

        return "all";
    }

    @RequestMapping(value = "/add_form", method = RequestMethod.GET)
    public String showAddForm(Model model) {

        model.addAttribute("event", new Event());

        return "add_form";
    }

    @RequestMapping(value = "/add_form", method = RequestMethod.POST)
    public String addEvent(Model model, @ModelAttribute("event") Event event) {

        String name = event.getName();
        String address = event.getAddress();
        String coord = String.valueOf(event.getCoord());

        if (name.length() > 0 && address.length() > 0 && coord.length() > 0) {
            if (event.getId() == null)
                eventService.add(event);
            else
                eventService.update(event);

            return "redirect:/all";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "add_form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEditForm(Model model, @RequestParam(required = true) Long id) {

        model.addAttribute("event", eventService.get(id));
        //return new ModelAndView("add_form", "event", eventService.get(id));

        return "add_form";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteEvent(@RequestParam(required = true) Long id) {

        eventService.remove(id);

        return "redirect:/all";
    }

    @RequestMapping(value = "/random_gen", method = RequestMethod.GET)
    public String generateRandomEvents() throws IOException, JSONException {

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
            t = (int) (Math.random() * 12); //27.43 27.67

            coord = rand_lng + "," + rand_lat;
            coord2 = rand_lat + "," + rand_lng;

            String requestUrl = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=" + apikey + "&geocode=" + coord;

            JSONObject json = new JSONObject(IOUtils.toString(new URL(requestUrl), Charset.forName("UTF-8")));
            JSONObject response = (JSONObject) json.get("response");
            JSONObject GeoObjectCollection = (JSONObject) response.get("GeoObjectCollection");
            JSONArray featureMember = (JSONArray) GeoObjectCollection.get("featureMember");
            JSONObject member = featureMember.getJSONObject(0);
            JSONObject GeoObject = (JSONObject) member.get("GeoObject");
            String address = (String) GeoObject.get("name");

            Event event = new Event(events[t], address, coord2);
            eventService.add(event);
        }

        return "redirect:/all";
    }
}