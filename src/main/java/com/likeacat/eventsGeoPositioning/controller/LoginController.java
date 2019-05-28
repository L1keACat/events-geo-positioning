package com.likeacat.eventsGeoPositioning.controller;

import com.likeacat.eventsGeoPositioning.model.User;
import com.likeacat.eventsGeoPositioning.services.CustomUserDetailsService;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Api(value="Login Controller", description="All operations with user authentication")
@Controller
public class LoginController {

    @Value("${deleteadminerror.message}")
    private String errorMessage;
    @Value("${userexistserror.message}")
    private String errorMessage2;

    @Autowired
    private CustomUserDetailsService userService;

    @ApiOperation(value = "View a login page")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @ApiOperation(value = "View a sign up page")
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @ApiOperation(value = "Create a user")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            modelAndView.addObject("errorMessage", errorMessage2);
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @ApiOperation(value = "View admin dashboard page")
    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("username", "Welcome " + user.getUsername());

        modelAndView.addObject("users", userService.getAll());
        modelAndView.setViewName("admin/dashboard");
        return modelAndView;
    }

    @ApiOperation(value = "Delete user")
    @RequestMapping(value = {"/user/{username}/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteUser(@ApiParam(value = "Id of user who will be edited", required = true)
                                       @PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        if(userService.checkForAdmin(username)) {
            //modelAndView.addObject("errorMessage", errorMessage);
            modelAndView.setViewName("redirect:/admin/dashboard");
        }
        else {
            userService.deleteUser(username);
            modelAndView.setViewName("redirect:/admin/dashboard");
        }
        return modelAndView;
    }
}
