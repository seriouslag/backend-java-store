package com.chiefsretro.controllers;

import com.chiefsretro.repositories.ProductRepository;
import com.chiefsretro.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("/product")
    public Product Product(@RequestParam("productId") Integer productId ) {
        Product product = productRepository.getProductByProductId(productId);
        if(product.getProductName() != null) {
            System.out.println("Returning: " + product.getProductName());
        }
        return product;
    }

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("/search")
    public Set<Product> Product(@RequestParam("productName") String productName) {
        productName = productName.trim();
        System.out.println(productName);
        Set<Product> products = productRepository.findDistinctFirst5ByProductNameLikeIgnoreCaseOrderByProductName("%" + productName + "%");

        return products;
    }

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("all")
    public Set<Product> Product() {
        System.out.println("Returning all products");
        return productRepository.findAllByOrderByProductName();
    }

    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("searchpage")
    public Page<Product> Product(@RequestParam("page") int pageNum, @RequestParam("itemsPerPage") int itemsPerPage) {
        PageRequest pageRequest = new PageRequest(pageNum, itemsPerPage);
        Page<Product> products = productRepository.findAllByOrderByProductName(pageRequest);
        return products;
    }
}