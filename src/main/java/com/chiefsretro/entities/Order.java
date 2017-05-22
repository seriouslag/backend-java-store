package com.chiefsretro.entities;

import java.util.List;

public class Order {

    private String email;
    private double date;
    private int total;
    private List<DbCartItem> cart;
    private StripeToken token;
    private StripeArgs args;
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DbCartItem> getCart() {
        return cart;
    }

    public void setCart(List<DbCartItem> cart) { this.cart = cart; }

    public StripeToken getToken() { return token; }

    public void setToken(StripeToken token) {
        this.token = token;
    }

    public StripeArgs getArgs() {
        return args;
    }

    public void setArgs(StripeArgs args) {
        this.args = args;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTokenAsString() {
       return this.token.toString();
    }


}
