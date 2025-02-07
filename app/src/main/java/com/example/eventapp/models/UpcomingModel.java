package com.example.eventapp.models;

public class UpcomingModel {
    private String title;
    private int imageResId; // Drawable resource ID

    public UpcomingModel(String title, int imageResId) {
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

