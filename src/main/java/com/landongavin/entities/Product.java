package com.landongavin.entities;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "product", fetch= FetchType.EAGER)
    @OrderBy("id asc")
    private Set<ProductOption> options;

    public Set<ProductOption> getOptions() { return options; }
    public void setOptions(Set<ProductOption> options) { this.options = options; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDefaultImageUrl() {
        Optional<String> firstOptionWithLocation = this.options.stream()
                .map(productOption -> {
                   Optional<String> location = productOption.getImages().stream()
                            .filter(currentImage -> !currentImage.getLocation().equals(""))
                            .findFirst()
                            .map(ProductOptionImage::getLocation);

                    return location.orElse("");

                }).findFirst();

        return firstOptionWithLocation.orElse("");

    }
}
