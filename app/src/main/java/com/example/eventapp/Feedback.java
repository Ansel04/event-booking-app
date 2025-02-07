package com.example.eventapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText reviewInput;
    private Button submitReview;
    private DatabaseReference databaseRef; // Firebase Database Reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        reviewInput = findViewById(R.id.reviewInput);
        submitReview = findViewById(R.id.submitReview);

        // Initialize Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("Reviews");

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });
    }

    private void submitReview() {
        float rating = ratingBar.getRating();
        String review = reviewInput.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Please select a rating!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (review.isEmpty()) {
            Toast.makeText(this, "Please write a review!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creating a unique key for each review
        String reviewId = databaseRef.push().getKey();

        // Creating a review object
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("rating", rating);
        reviewData.put("review", review);
        reviewData.put("timestamp", System.currentTimeMillis());

        // Store in Realtime Database
        databaseRef.child(reviewId).setValue(reviewData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Updated in database", Toast.LENGTH_SHORT).show();
                    ratingBar.setRating(0); // Reset Rating
                    reviewInput.setText(""); // Clear input field
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update!", Toast.LENGTH_SHORT).show());
    }
}
