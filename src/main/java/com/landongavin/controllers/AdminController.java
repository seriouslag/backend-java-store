package com.landongavin.controllers;

import com.landongavin.repositories.ProductRepository;
import com.landongavin.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://landongavin.com", "http://localhost:8080"})
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public String Product(@RequestBody Product product, @RequestAttribute String uid, @RequestAttribute String role) {
        if (!role.equals("admin")) {
            // TODO throw error response;
            return "You are not an admin.";
        }
        if (productRepository.findFirstByProductName(product.getProductName()) != null) {
            return "A product with a name of " + product.getProductName() + " already exists.";
        }

        productRepository.save(product);
        return "Added product " + product.getProductName();
    }

    @PutMapping("/product/{id}")
    public String Product(@RequestBody Product product, @PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) {
        if (!role.equals("admin")) {
            // TODO throw error response;
            return "You are not an admin.";
        }
        if(product.getProductId() != id) {
            return "Product id of supplied product does not match path id";
        }
        if (productRepository.existsProductByProductId(id) == false) {
            return "A product with an id of " + id + " doesn't exist.";
        }

        productRepository.save(product);
        return "Updated product " + product.getProductName();
    }

    @Transactional
    @DeleteMapping("/product/{id}")
    public String Product(@PathVariable("id") int id, @RequestAttribute String uid, @RequestAttribute String role) {
        if (!role.equals("admin")) {
            // TODO throw error response;
            return "You are not an admin.";
        }
        if (productRepository.existsProductByProductId(id) == false) {
            return "A product with an id of " + id + " doesn't exist.";
        }

        productRepository.deleteProductByProductId(id);
        return "Deleted product with id of " + id;
    }

    @GetMapping("/example")
    public String AdminExample(@RequestAttribute String uid, @RequestAttribute String role) {
        if (!role.equals("admin")) {
            // TODO throw error response;
            return "You are not an admin.";
        }

        return "You are an admin with uid of " + uid;
    }
}