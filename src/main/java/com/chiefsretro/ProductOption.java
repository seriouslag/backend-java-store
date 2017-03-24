package com.chiefsretro;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Landon on 3/21/2017.
 */
@Entity
@Table(name = "product_options")
public class ProductOption {

    @Id
    private Integer productOptionsId;

    private String productColor;

    private double productPrice;

    private Integer productQuantity;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    //@JoinColumn(name = "product_id")
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
}
