package com.landongavin.controllers;

import com.landongavin.entities.Exceptions.NotAuthorizedException;
import com.landongavin.entities.Exceptions.UserExistsButIsNotAFirebaseUserException;
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
@CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://landongavin.com", "http://localhost:8080"})
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final UserController userController;

    @Autowired
    public AdminController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userController = new UserController(userRepository);
    }

    @PostMapping("/addadmin")
    public boolean addAdmin(@RequestBody long id, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorizedException, NotFoundException, UserExistsButIsNotAFirebaseUserException {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorizedException("by " + uid);
        }
        User user = userController.User(id);
        if (user == null) {
            throw new NotFoundException("User of id " + id + " was not found.");
        }

        if (user.getFirebaseUid().equals("") == true) {
            throw new UserExistsButIsNotAFirebaseUserException("User of id: " + id + " is not in the firebase database but is saved in the local database. :(");
        }

        return FirebaseService.addRoleToFirebaseUser(user.getFirebaseUid(), "admin");
    }

    @PostMapping("/product")
    public String Product(@RequestBody Product product, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorizedException {
        if (!role.equals("admin")) {
            throw new NotAuthorizedException("by " + uid);
        }
        if (productRepository.findFirstByName(product.getName()) != null) {
            return "A product with a name of " + product.getName() + " already exists.";
        }

        productRepository.save(product);
        return "Added product " + product.getName();
    }

    @PutMapping("/product/{id}")
    public String Product(@RequestBody Product product, @PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorizedException, BadHttpRequest, NotFoundException {
        if (!role.equals("admin")) {
            throw new NotAuthorizedException("by " + uid);
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
    public String Product(@PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) throws NotFoundException, NotAuthorizedException {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorizedException("by " + uid);
        }
        if (productRepository.existsProductById(id) == false) {
            throw new NotFoundException("A product with an id of " + id + " doesn't exist.");
        }

        productRepository.deleteProductById(id);
        return "Deleted product with id of " + id;
    }

    @GetMapping("/example")
    public String AdminExample(@RequestAttribute String uid, @RequestAttribute String role) throws NotAuthorizedException {
        if (!role.equals("admin")) {
            // TODO throw error response;
            throw new NotAuthorizedException("by " + uid);
        }

        return "You are an admin with uid of " + uid;
    }
}