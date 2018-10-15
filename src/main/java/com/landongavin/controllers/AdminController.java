package com.landongavin.controllers;

import com.landongavin.entities.Exceptions.NotAuthorized;
import com.landongavin.entities.Exceptions.UserExistsButIsNotAFirebaseUser;
import com.landongavin.entities.User;
import com.landongavin.repositories.ProductRepository;
import com.landongavin.entities.Product;
import com.landongavin.repositories.UserRepository;
import com.landongavin.services.FirebaseService;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

    @Autowired
    public AdminController(ProductRepository productRepository, UserRepository userRepository, FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.firebaseService = firebaseService;
    }

    @PostMapping("/addadmin")
    public boolean addAdmin(@RequestBody long id, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorized, NotFoundException, UserExistsButIsNotAFirebaseUser {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorized("by " + uid);
        }
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User of id " + id + " was not found.");
        }

        if (user.getFirebaseUid().equals("") == true) {
            throw new UserExistsButIsNotAFirebaseUser("User of id: " + id + " is not in the firebase database but is saved in the local database. :(");
        }

        return firebaseService.addRoleToFirebaseUser(user.getFirebaseUid(), "admin");
    }

    @PostMapping("/product")
    public String Product(@RequestBody Product product, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorized {
        if (!role.equals("admin")) {
            throw new NotAuthorized("by " + uid);
        }
        if (productRepository.findFirstByName(product.getName()) != null) {
            return "A product with a name of " + product.getName() + " already exists.";
        }

        productRepository.save(product);
        return "Added product " + product.getName();
    }

    @PutMapping("/product/{id}")
    public String Product(@RequestBody Product product, @PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorized, BadHttpRequest, NotFoundException {
        if (!role.equals("admin")) {
            throw new NotAuthorized("by " + uid);
        }
        if(product.getId() != id) {
            throw new BadHttpRequest();
        }
        if (productRepository.existsProductById(id) == false) {
            throw new NotFoundException("A product with an id of " + id + " doesn't exist.");
        }

        productRepository.save(product);
        return "Updated product " + product.getName();
    }

    @Transactional
    @DeleteMapping("/product/{id}")
    public String Product(@PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) throws NotFoundException, NotAuthorized {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorized("by " + uid);
        }
        if (productRepository.existsProductById(id) == false) {
            throw new NotFoundException("A product with an id of " + id + " doesn't exist.");
        }

        productRepository.deleteProductById(id);
        return "Deleted product with id of " + id;
    }

    @GetMapping("/example")
    public String AdminExample(@RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorized {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorized("by " + uid);
        }

        return "You are an admin with uid of " + uid;
    }
}