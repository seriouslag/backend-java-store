package com.landongavin.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "product_suboptions")
public class ProductSuboption {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String type;
    private double price;
    private int quantity;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_option_id", insertable = false, updatable = false)
    @JsonIgnore
    private ProductOption option;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public ProductOption getOption() { return option; }
    public void setOption(ProductOption productOption) { this.option = productOption; }
}
