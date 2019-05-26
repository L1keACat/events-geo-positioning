package com.likeacat.eventsGeoPositioning;

import com.likeacat.eventsGeoPositioning.model.Role;
import com.likeacat.eventsGeoPositioning.model.User;
import com.likeacat.eventsGeoPositioning.repository.RoleRepository;
import com.likeacat.eventsGeoPositioning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class Application {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }

            User admin = userRepository.findByUsername("admin");
            if (admin == null) {
                User newAdmin = new User();
                newAdmin.setUsername("admin");
                newAdmin.setPassword("123");
                newAdmin.setPassword(bCryptPasswordEncoder.encode(newAdmin.getPassword()));
                newAdmin.setEnabled(true);
                Role newRole = roleRepository.findByRole("ADMIN");
                newAdmin.setRoles(new HashSet<>(Arrays.asList(newRole)));
                userRepository.save(newAdmin);
            }
        };
    }

}
