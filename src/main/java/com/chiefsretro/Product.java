package com.chiefsretro;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Landon on 3/16/2017.
 */
@Entity
public class Product {

    @Id
    private Integer productId;

    private String productName;

    private String productImage;

    private String productDescription;

    private double productPrice;

    private Integer productQuantity;

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {this.productId = productId; }

    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }
}
