package com.landongavin.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Landon on 3/21/2017.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "product_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int productOptionsId;

    private String productType;

    private double productPrice;

    private int productQuantity;

    @OneToMany(mappedBy = "productOption", fetch=FetchType.EAGER)
   // @JsonManagedReference
    @OrderBy("productOptionImageOrder asc")
    @SortNatural
    private Set<ProductOptionImage> productOptionImages;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private Product product;



    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getProductType() {
        return productType;
    }
    public void setProductType(String productColor) { this.productType = productColor; }


    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(int productQuantity) { this.productQuantity = productQuantity; }

    public int getProductOptionId() {
        return productOptionsId;
    }
    public void setProductOptionId(int productOptionId) {
        this.productOptionsId = productOptionId;
    }

    public Set<ProductOptionImage> getProductOptionImages() {return this.productOptionImages;}
    public void setProductOptionImages(Set<ProductOptionImage> productOptionImages) {
        this.productOptionImages = productOptionImages;
    }
}
