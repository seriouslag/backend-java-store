package com.landongavin.repositories;

import com.landongavin.entities.ProductOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductOptionRepository extends CrudRepository<ProductOption, Integer> {
    Set<ProductOption> getProductsOptionByProductId(int id);
}
