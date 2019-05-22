package com.likeacat.eventsGeoPositioning.controller;

import com.likeacat.eventsGeoPositioning.model.Event;
import com.likeacat.eventsGeoPositioning.services.EventService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {
    @Autowired
    private EventService eventService;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/","/start"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("start");
        return modelAndView;
    }

    @RequestMapping(value = {"/all"}, method = RequestMethod.GET)
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events", eventService.getAll());
        modelAndView.setViewName("all");
        return modelAndView;
    }

    @RequestMapping(value = {"/all_guest"}, method = RequestMethod.GET)
    public ModelAndView showAllGuest() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events", eventService.getAll());
        modelAndView.setViewName("all_guest");
        return modelAndView;
    }

    @RequestMapping(value = {"/add_form"}, method = RequestMethod.GET)
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("event", new Event());
        modelAndView.setViewName("add_form");
        return modelAndView;
    }

    @RequestMapping(value = {"/add_form"}, method = RequestMethod.POST)
    public ModelAndView addEvent(@ModelAttribute("event") Event event) {
        ModelAndView modelAndView = new ModelAndView();

        String name = event.getName();
        String address = event.getAddress();
        String coord = String.valueOf(event.getCoord());
        if (name.length() > 0 && address.length() > 0 && coord.length() > 0) {
            if (event.getId() == null)
                eventService.add(event);
            else
                eventService.update(event);

            modelAndView.setViewName("redirect:/all");
            return modelAndView;
        }
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.setViewName("add_form");
        return modelAndView;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public ModelAndView showEditForm(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("event", eventService.get(id));
        modelAndView.setViewName("add_form");
        return modelAndView;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteEvent(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        eventService.remove(id);
        modelAndView.setViewName("redirect:/all");
        return modelAndView;
    }

    @RequestMapping(value = {"/random_gen"}, method = RequestMethod.GET)
    public ModelAndView generateRandomEvents() throws IOException, JSONException {
        ModelAndView modelAndView = new ModelAndView();
        eventService.random_gen();
        modelAndView.setViewName("redirect:/all");
        return modelAndView;
    }
}