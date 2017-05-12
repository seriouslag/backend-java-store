package com.chiefsretro.controllers;

import com.chiefsretro.repositories.UserRepository;
import com.chiefsretro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("/user")
    public User User(@RequestParam("userEmail") String userEmail ) {
        User user = userRepository.getUserByUserEmail(userEmail);
        if(user != null) {
            System.out.println("Returning: " + user.getUserFname() + " " + user.getUserLname());
        }
        return user;
    }

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("/all")
    public Set<User> User() {
        System.out.println("Returning all users");
        return userRepository.getAllByOrderByUserId();
    }


}