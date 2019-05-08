package com.likeacat.eventsGeoPositioning.web;

import com.likeacat.eventsGeoPositioning.model.Event;
import com.likeacat.eventsGeoPositioning.services.EventService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

        return "add_form";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteEvent(@RequestParam(required = true) Long id) {

        eventService.remove(id);

        return "redirect:/all";
    }

    @RequestMapping(value = "/random_gen", method = RequestMethod.GET)
    public String generateRandomEvents() throws IOException, JSONException {

        eventService.random_gen();

        return "redirect:/all";
    }
}