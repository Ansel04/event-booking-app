package com.example.eventapp.models;

public class CategoryModel {

    private String name;
    private int imageResId;

    // Constructor
    public CategoryModel(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
