package com.landongavin.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_option_images")
public class ProductOptionImage {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int productOptionImageId;

    private String productOptionImageLocation;

    private boolean hasThumb;

    private int productOptionImageOrder;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "product_options_id", insertable = false, updatable = false)
    //@JsonManagedReference
    private ProductOption productOption;

    public ProductOption getProductOption() {return this.productOption;}
    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public int getProductOptionImageId() {return this.productOptionImageId;}
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

    public int getProductOptionImageOrder() {return this.productOptionImageOrder;}
    public void setProductOptionImageOrder(int productOptionImageOrder) {this.productOptionImageOrder = productOptionImageOrder;}
}
