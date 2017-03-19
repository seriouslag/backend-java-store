package com.chiefsretro.old;


import com.chiefsretro.Product;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Landon on 3/18/2017.
 */

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private SessionFactory sessionFactory;

     public Product findByProductId(Integer productId) {
     Criteria criteria = sessionFactory.openSession().createCriteria(Product.class).add(Restrictions.eq("productId", productId));

     try {

         return ((Product) criteria.list().get(1));
     } catch (Exception e) {
         System.out.println();
     }
     return null;
    }
}
