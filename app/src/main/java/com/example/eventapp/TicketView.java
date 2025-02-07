package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketView extends AppCompatActivity {

    private TextView ticketDate, ticketSeatType, ticketNumSeats;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private static final String TAG = "TicketView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        ticketDate = findViewById(R.id.ticket_date);
        ticketSeatType = findViewById(R.id.ticket_seat_type);
        ticketNumSeats = findViewById(R.id.ticket_num_seats);

        // Check if user is logged in
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Please log in to view your tickets!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = user.getUid();

        // Get Ticket ID from Intent
        Intent intent = getIntent();
        String ticketId = intent.getStringExtra("ticketId");

        if (ticketId == null) {
            Toast.makeText(this, "No ticket ID provided!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d(TAG, "Fetching ticket: " + ticketId + " for user: " + userId);

        // Correct Firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Tickets").child(ticketId);

        // Retrieve ticket data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() { // Real-time updates
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String date = snapshot.child("eventDate").getValue(String.class);
                    Integer numSeats = snapshot.child("ticketCount").getValue(Integer.class);
                    String seatType = snapshot.child("seatType").getValue(String.class);

                    Log.d(TAG, "Ticket data found: Date=" + date + ", Seats=" + numSeats + ", Type=" + seatType);

                    // Display Data
                    ticketDate.setText("Date: " + (date != null ? date : "N/A"));
                    ticketSeatType.setText("Seat Type: " + (seatType != null ? seatType : "N/A"));
                    ticketNumSeats.setText("Seats: " + (numSeats != null ? numSeats : "0"));
                } else {
                    Toast.makeText(TicketView.this, "No ticket found!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Ticket not found for ID: " + ticketId);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TicketView.this, "Failed to load ticket: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Firebase error: " + error.getMessage());
            }
        });
    }
}
