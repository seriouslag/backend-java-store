package com.chiefsretro;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product getProductByProductId(Integer ProductId);
    Set<Product> getFirst10ByProductNameIgnoreCaseContaining(String string);
    Set<Product> getDistinctFirst10ByProductNameIgnoreCaseContaining(String string);
    Set<Product> getProductsByProductNameIgnoreCaseContains(String string);
    Set<Product> findTop5ByProductNameIgnoreCaseContaining(String string);

    //@Query("select p from Product p where lower(p.productName) like lower(?1)")
    Set<Product> findDistinctFirst5ByProductNameLikeIgnoreCaseOrderByProductName(String string);
}
