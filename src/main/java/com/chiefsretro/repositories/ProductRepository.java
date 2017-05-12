package com.chiefsretro.repositories;

import com.chiefsretro.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product getProductByProductId(Integer ProductId);
    Set<Product> findDistinctFirst5ByProductNameLikeIgnoreCaseOrderByProductName(String string);
    Set<Product> findAllByOrderByProductName();
    Page<Product> findAllByOrderByProductName(Pageable page);
}
