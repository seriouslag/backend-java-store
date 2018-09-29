package com.landongavin.entities;

public class SearchProduct {
    private int id;
    private String name;
    private String description;
    private String defaultImage;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDefaultImage() { return defaultImage; }
    public void setDefaultImage(String defaultImage) { this.defaultImage = defaultImage; }
}
