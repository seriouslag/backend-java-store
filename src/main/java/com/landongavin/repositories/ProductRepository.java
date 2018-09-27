package com.landongavin.repositories;

import com.landongavin.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product getProductById(Integer ProductId);
    Set<Product> findDistinctFirst5ByNameLikeIgnoreCaseOrderByName(String string);
    Set<Product> findAllByOrderByName();
    Page<Product> findAllByOrderByName(Pageable page);
    Product save(Product product);
    Product findFirstByName(String name);
    boolean existsProductById(int id);
    @Transactional
    int deleteProductById(int id);
}
