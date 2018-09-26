package com.landongavin.entities;

import java.util.List;

public class Order {

    private String email;
    private double date;
    private long total;
    private List<DbCartItem> cart;
    private StripeToken token;
    private StripeArgs args;
    private String status;
    private Long amount;
    private String chargeId;

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    private String orderMessage;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
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
