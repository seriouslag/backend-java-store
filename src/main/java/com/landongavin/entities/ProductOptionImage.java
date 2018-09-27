package com.landongavin.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "product_option_images")
public class ProductOptionImage {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String location;
    private boolean hasThumb;
    private int order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_options_id", insertable = false, updatable = false)
    @JsonIgnore
    private ProductOption option;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public boolean getHasThumb() {
        return this.hasThumb;
    }
    public void setHasThumb(boolean hasThumb) { this.hasThumb = hasThumb; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    public ProductOption getProductOption() { return option; }
    public void setProductOption(ProductOption option) { this.option = option; }
}
