package com.landongavin.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jdk.nashorn.internal.runtime.options.Option;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        String location = this.options.stream()
                .map(option -> {
                    Optional<ProductOptionImage> firstImage = option.getImages().stream().filter(image -> !image.getLocation().equals("")).findFirst();
                     if (firstImage.isPresent()) {
                         return firstImage.get().getLocation();
                     }
                     return "";
                }).findFirst().get();
        return location;
    }
}
