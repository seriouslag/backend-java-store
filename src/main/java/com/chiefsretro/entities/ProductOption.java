package com.chiefsretro.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Integer productOptionsId;

    private String productColor;

    private double productPrice;

    private Integer productQuantity;

    @OneToMany(mappedBy = "productOption", fetch=FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("productOptionImageOrder asc")
    @SortNatural
    private Set<ProductOptionImage> productOptionImages;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private Product product;



    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getProductColor() {
        return productColor;
    }
    public void setProductColor(String productColor) { this.productColor = productColor; }


    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }

    public Integer getProductOptionId() {
        return productOptionsId;
    }
    public void setProductOptionId(Integer productOptionId) {
        this.productOptionsId = productOptionId;
    }

    public Set<ProductOptionImage> getProductOptionImages() {return this.productOptionImages;}
    public void setProductOptionImages(Set<ProductOptionImage> productOptionImages) {
        this.productOptionImages = productOptionImages;
    }
}
