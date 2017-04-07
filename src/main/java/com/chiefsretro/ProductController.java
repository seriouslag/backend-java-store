package com.chiefsretro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @CrossOrigin(origins = "http://seriouslag.com:4200")
    @GetMapping("/product")
    public Product Product(@RequestParam("productId") Integer productId ) {
        Product product = productRepository.getProductByProductId(productId);
        return product;
    }

    @CrossOrigin(origins = "http://seriouslag.com:4200")
    @GetMapping("/all")
    public Set<Product> Product(@RequestParam("productName") String productName) {
        productName = productName.trim();
        System.out.println(productName);
        Set<Product> products = productRepository.findDistinctFirst5ByProductNameLikeIgnoreCaseOrderByProductName("%" + productName + "%");
        System.out.println(products.size());
        return products;
    }
}