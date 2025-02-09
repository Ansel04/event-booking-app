package com.example.eventapp.models;

public class MusicModel {
    private String title;
    private int imageResId; // Drawable resource ID

    public MusicModel(String title, int imageResId) {
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

