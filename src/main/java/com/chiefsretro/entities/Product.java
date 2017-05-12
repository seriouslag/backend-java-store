package com.chiefsretro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "products")
public class Product {
    @Id
    private Integer productId;

    private String productName;

    private String productDescription;

    @OneToMany(mappedBy = "product", fetch= FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("productOptionsId asc")
    private Set<ProductOption> productOptions;


    public Set<ProductOption> getProductOptions() {
        return productOptions;
    }
    public void setProductOptions(Set<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }


    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) { this.productName = productName; }


    public double getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {this.productId = productId; }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

}
