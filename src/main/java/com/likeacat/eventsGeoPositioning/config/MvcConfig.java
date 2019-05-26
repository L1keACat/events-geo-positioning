package com.likeacat.eventsGeoPositioning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start");
        registry.addViewController("/start").setViewName("start");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/all").setViewName("all");
        registry.addViewController("/all_guest").setViewName("all_guest");
        registry.addViewController("/all_admin").setViewName("all_admin");
        registry.addViewController("/add_form").setViewName("add_form");
        registry.addViewController("/add_form_admin").setViewName("add_form_admin");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/about_guest").setViewName("about_guest");
        registry.addViewController("/about_admin").setViewName("about_admin");
        registry.addViewController("/dashboard").setViewName("dashboard");
    }

}