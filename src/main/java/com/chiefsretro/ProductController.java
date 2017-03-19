package com.chiefsretro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Landon on 3/16/2017.
 */

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/product")
    public Product Product(@RequestParam("productId") Integer productId ) {
        System.out.println( productRepository.findAll());
        return productRepository.findOne(productId);
    }
}