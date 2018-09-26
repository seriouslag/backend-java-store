package com.landongavin.entities;

/**
 * Created by Landon on 5/21/2017.
 */
public class DbCartItem {

    private int productId;
    private long productOptionPrice;
    private int productOptionId;
    private int quantity;
    private double dateAdded;

    public double getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductOptionPrice() {
        return productOptionPrice;
    }

    public void setProductOptionPrice(int productOptionPrice) {
        this.productOptionPrice = productOptionPrice;
    }

    public double getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(int productOptionId) {
        this.productOptionId = productOptionId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(double dateAdded) {
        this.dateAdded = dateAdded;
    }


}
