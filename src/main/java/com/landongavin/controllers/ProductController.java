package com.landongavin.controllers;

import com.landongavin.entities.Exceptions.NotFoundException;
import com.landongavin.repositories.ProductOptionRepository;
import com.landongavin.repositories.ProductRepository;
import com.landongavin.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://landongavin.com", "http://localhost:8080"})
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @GetMapping("/product/{id}")
    public Product Product(@PathVariable("id") int id) {
        Product product = productRepository.getProductById(id);

        if(product != null && product.getName() != null) {
            System.out.println("Returning: " + product.getName());
        } else {
            throw new NotFoundException("The product of id " + id + " was not found.");
        }
        return product;
    }

    @GetMapping("/search")
    public Set<Product> Product(@RequestParam("productName") String productName) {
        productName = productName.trim();
        System.out.println(productName);
        Set<Product> products = productRepository.findDistinctFirst5ByNameLikeIgnoreCaseOrderByName("%" + productName + "%");

        return products;
    }

    @GetMapping("/product/all")
    public Set<Product> Product() {
        System.out.println("Returning all products");
        return productRepository.findAllByOrderByName();
    }

    @GetMapping("/searchpage")
    public Page<Product> Product(@RequestParam("page") int pageNum, @RequestParam("itemsPerPage") int itemsPerPage) {
        PageRequest pageRequest = PageRequest.of(pageNum, itemsPerPage);
        Page<Product> products = productRepository.findAllByOrderByName(pageRequest);
        return products;
    }
}