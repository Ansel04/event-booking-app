package com.example.eventapp.models;

public class MusicModel {

    private String name;
    private int imageResId;

    // Constructor
    public MusicModel(String name, int imageResId) {
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
