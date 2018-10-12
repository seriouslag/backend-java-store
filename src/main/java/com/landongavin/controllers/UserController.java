package com.landongavin.controllers;

import com.landongavin.entities.Exceptions.BadModel;
import com.landongavin.entities.Exceptions.NotFound;
import com.landongavin.entities.Exceptions.UserExistsButNotSaved;
import com.landongavin.entities.User;
import com.landongavin.repositories.UserRepository;
import com.landongavin.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}")
    public User User(@PathVariable("id") long id) {
        User user = userRepository.getUserById(id);

        if(user != null) {
            System.out.println("Returning: " + user.getName());
        } else {
            throw new NotFound("The user of id " + id + " was not found.");
        }
        return user;
    }

    @PutMapping("/user")
    @PostMapping("/user")
    public User User(@RequestBody User user) throws UserExistsButNotSaved {
        if (user.getFirebaseUid() == null) {
            throw new BadModel("No firebase uid included.");
        }

        if (FirebaseService.firebaseUserExists(user.getFirebaseUid()) == false) {
            throw new NotFound("Firebase user not found.");
        }

        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            return savedUser;
        }

        throw new UserExistsButNotSaved("Tried to save user with firebase uid of " + user.getFirebaseUid() + " but it failed to save to DB");
    }
}
