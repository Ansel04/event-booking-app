package com.example.eventapp.models;

public class SportsModel {
    private String title;
    private int imageResId; // Drawable resource ID

    public SportsModel(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}

