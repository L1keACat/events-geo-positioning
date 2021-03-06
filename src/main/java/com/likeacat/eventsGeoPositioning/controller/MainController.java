package com.likeacat.eventsGeoPositioning.controller;

import com.likeacat.eventsGeoPositioning.model.Event;
import com.likeacat.eventsGeoPositioning.model.User;
import com.likeacat.eventsGeoPositioning.services.CustomUserDetailsService;
import com.likeacat.eventsGeoPositioning.services.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Api(value="Rest Controller", description="REST-operations with MongoDB database and other")
@RestController
public class MainController {
    @Autowired
    private EventService eventService;
    @Autowired
    private CustomUserDetailsService userService;

    @Value("${error.message}")
    private String errorMessage;

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }

    @ApiOperation(value = "View a start page")
    @RequestMapping(value = {"/","/start"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("start");
        return modelAndView;
    }

    @ApiOperation(value = "View a page with all events")
    @RequestMapping(value = {"/user/all", "/admin/all_admin"}, method = RequestMethod.GET)
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events", eventService.getAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        if(userService.checkForAdmin(user.getUsername())) {
            modelAndView.setViewName("admin/all_admin");
        }
        else {
            modelAndView.setViewName("user/all");
        }
        return modelAndView;
    }

    @ApiOperation(value = "View a guest page with all events")
    @RequestMapping(value = {"/all_guest"}, method = RequestMethod.GET)
    public ModelAndView showAllGuest() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events", eventService.getAll());
        modelAndView.setViewName("all_guest");
        return modelAndView;
    }

    @ApiOperation(value = "View a guest page about app")
    @RequestMapping(value = {"/about_guest"}, method = RequestMethod.GET)
    public ModelAndView showAboutGuest() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about_guest");
        return modelAndView;
    }

    @ApiOperation(value = "View a add event page")
    @RequestMapping(value = {"/user/add_form", "/admin/add_form_admin"}, method = RequestMethod.GET)
    public ModelAndView GetAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("event", new Event());
        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("admin/add_form_admin");
        }
        else {
            modelAndView.setViewName("user/add_form");
        }
        return modelAndView;
    }

    @ApiOperation(value = "Add an event")
    @RequestMapping(value = {"/user/add_form", "/admin/add_form_admin"}, method = RequestMethod.POST)
    public ModelAndView PostAddForm(@ModelAttribute("event") Event event) {
        ModelAndView modelAndView = new ModelAndView();

        String name = event.getName();
        String address = event.getAddress();
        String coord = String.valueOf(event.getCoord());
        if (name.length() > 0 && address.length() > 0 && coord.length() > 0) {
            if (event.getId() == null)
                eventService.add(event);
            else
                eventService.update(event);

            if(userService.checkForAdmin(getUser().getUsername())) {
                modelAndView.setViewName("redirect:/admin/all_admin");
            }
            else {
                modelAndView.setViewName("redirect:/user/all");
            }
            return modelAndView;
        }
        modelAndView.addObject("errorMessage", errorMessage);
        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("admin/add_form_admin");
        }
        else {
            modelAndView.setViewName("user/add_form");
        }
        return modelAndView;
    }

    @ApiOperation(value = "View an edit event page")
    @RequestMapping(value = {"/event/{id}/edit"}, method = RequestMethod.GET)
    public ModelAndView GetEditForm(@ApiParam(value = "Id of event which will be edited", required = true)
                                        @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("event", eventService.findById(id));
        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("admin/add_form_admin");
        }
        else {
            modelAndView.setViewName("user/add_form");
        }
        return modelAndView;
    }

    @ApiOperation(value = "Delete event")
    @RequestMapping(value = {"/event/{id}/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteEvent(@ApiParam(value = "Id of event which will be deleted", required = true)
                                        @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        eventService.remove(id);

        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("redirect:/admin/all_admin");
        }
        else {
            modelAndView.setViewName("redirect:/user/all");
        }

        return modelAndView;
    }

    @ApiOperation(value = "Generate 10 random events")
    @RequestMapping(value = {"/random_gen"}, method = RequestMethod.GET)
    public ModelAndView generateRandomEvents() throws IOException, JSONException {
        ModelAndView modelAndView = new ModelAndView();
        eventService.random_gen();

        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("redirect:/admin/all_admin");
        }
        else {
            modelAndView.setViewName("redirect:/user/all");
        }

        return modelAndView;
    }

    @ApiOperation(value = "View a page about app")
    @RequestMapping(value = {"/user/about", "/admin/about_admin"}, method = RequestMethod.GET)
    public ModelAndView AboutPage() {
        ModelAndView modelAndView = new ModelAndView();

        if(userService.checkForAdmin(getUser().getUsername())) {
            modelAndView.setViewName("admin/about_admin");
        }
        else {
            modelAndView.setViewName("user/about");
        }

        return modelAndView;
    }
}