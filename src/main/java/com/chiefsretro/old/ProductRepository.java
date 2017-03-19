package com.chiefsretro.old;

import com.chiefsretro.Product;

import java.util.List;


/**
 * Created by Landon on 3/18/2017.
 */
public interface ProductRepository {

    Product findByProductId(Integer productId);
}


