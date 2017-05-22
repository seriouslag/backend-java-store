package com.chiefsretro.entities;

/**
 * Created by Landon on 5/21/2017.
 */
public class DbCartItem {

    private double productId;
    private double productOptionPrice;
    private double productOptionId;
    private double quantity;
    private double dateAdded;

    public double getProductId() {
        return productId;
    }

    public void setProductId(double productId) {
        this.productId = productId;
    }

    public double getProductOptionPrice() {
        return productOptionPrice;
    }

    public void setProductOptionPrice(double productOptionPrice) {
        this.productOptionPrice = productOptionPrice;
    }

    public double getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(double productOptionId) {
        this.productOptionId = productOptionId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(double dateAdded) {
        this.dateAdded = dateAdded;
    }


}
