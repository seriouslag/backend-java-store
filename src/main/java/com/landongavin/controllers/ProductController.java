package com.landongavin.controllers;

import com.landongavin.entities.Exceptions.NotFound;
import com.landongavin.entities.SearchProduct;
import com.landongavin.repositories.ProductOptionRepository;
import com.landongavin.repositories.ProductRepository;
import com.landongavin.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @GetMapping("/product/{id}")
    public Product Product(@PathVariable("id") int id) {
        Product product = productRepository.getProductById(id);

        if(product != null && product.getName() != null) {
            System.out.println("Returning: " + product.getName());
        } else {
            throw new NotFound("The product of id " + id + " was not found.");
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

    @GetMapping("/test/search")
    public Set<SearchProduct> Search(@RequestParam("productName") String productName) {
        Set<Product> products = Product(productName);
        return products.stream()
                .map(product -> {
                    SearchProduct sp = new SearchProduct();
                    sp.setDescription(product.getDescription());
                    sp.setName(product.getName());
                    sp.setId(product.getId());
                    sp.setDefaultImage(product.getDefaultImageUrl());

                    return sp;
                }).collect(Collectors.toSet());

    }
}