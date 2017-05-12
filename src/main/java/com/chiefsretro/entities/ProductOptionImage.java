package com.chiefsretro.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "product_option_images")
public class ProductOptionImage {
    @Id
    private Integer productOptionImageId;

    private String productOptionImageLocation;

    private boolean hasThumb;

    private Integer productOptionImageOrder;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "product_options_id", insertable = false, updatable = false)
    @JsonManagedReference
    private ProductOption productOption;

    public ProductOption getProductOption() {return this.productOption;}
    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Integer getProductOptionImageId() {return this.productOptionImageId;}
    public void setProductOptionImageId(int productOptionImageId) {
        this.productOptionImageId = productOptionImageId;
    }

    public String getProductOptionImageLocation() {return this.productOptionImageLocation;}
    public void setProductOptionImageLocation(String productOptionImageLocation) {
        this.productOptionImageLocation = productOptionImageLocation;
    }

    public boolean getHasThumb() {return this.hasThumb;}
    public void setHasThumb(boolean hasThumb) {
        this.hasThumb = hasThumb;
    }

    public Integer getProductOptionImageOrder() {return this.productOptionImageOrder;}
    public void setProductOptionImageOrder(Integer productOptionImageOrder) {this.productOptionImageOrder = productOptionImageOrder;}
}
