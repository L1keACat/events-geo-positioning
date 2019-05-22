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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start");
        registry.addViewController("/start").setViewName("start");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/all").setViewName("all");
        registry.addViewController("/all_guest").setViewName("all_guest");
        registry.addViewController("/add_form").setViewName("add_form");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/dashboard").setViewName("dashboard");
    }

}