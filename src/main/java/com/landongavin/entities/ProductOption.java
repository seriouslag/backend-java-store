package com.landongavin.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "product_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String type;
    private double price;
    private int quantity;

    @OneToMany(mappedBy = "option", fetch=FetchType.EAGER)
    @OrderBy("order asc")
    @SortNatural
    private Set<ProductOptionImage> images;

    @OneToMany(mappedBy = "option", fetch=FetchType.EAGER)
    @SortNatural
    private Set<ProductSuboption> suboptions;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Set<ProductOptionImage> getImages() { return images; }
    public Set<ProductSuboption> getSuboptions() { return suboptions; }
    public void setImages(Set<ProductOptionImage> images) { this.images = images; }
    public void setSuboptions(Set<ProductSuboption> suboptions) {this.suboptions = suboptions; }
}
