package com.chiefsretro.old;

import com.chiefsretro.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Landon on 3/18/2017.
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product findByProductId(Integer productId) {
        return productRepository.findByProductId(productId);
    }
}
