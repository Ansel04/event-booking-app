package com.example.eventapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventViewActivity extends AppCompatActivity {

    private EditText datePicker;
    private NumberPicker ticketNumberPicker;
    private Spinner seatTypeSpinner;
    private Button bookTicketButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        datePicker = findViewById(R.id.datePicker);
        ticketNumberPicker = findViewById(R.id.ticketNumber);
        seatTypeSpinner = findViewById(R.id.seatType);
        bookTicketButton = findViewById(R.id.bookTicket);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Set min and max values for NumberPicker
        ticketNumberPicker.setMinValue(1);
        ticketNumberPicker.setMaxValue(10);
        ticketNumberPicker.setWrapSelectorWheel(true);

        // Set up Seat Type Spinner
        String[] seatTypes = {"Regular", "VIP", "Balcony"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, seatTypes);
        seatTypeSpinner.setAdapter(adapter);

        // Date Picker Dialog
        datePicker.setOnClickListener(view -> showDatePicker());

        // Book Ticket Button Click Listener
        bookTicketButton.setOnClickListener(view -> bookTicket());
    }

    private void bookTicket() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Please log in to book a ticket!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        int selectedTickets = ticketNumberPicker.getValue();
        String selectedDate = datePicker.getText().toString();
        String selectedSeatType = seatTypeSpinner.getSelectedItem().toString();

        if (selectedDate.isEmpty()) {
            Toast.makeText(EventViewActivity.this, "Please select a date!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to user's ticket list
        DatabaseReference userTicketsRef = databaseReference.child(userId).child("Tickets");

        // Generate a unique ID for each ticket
        String ticketId = userTicketsRef.push().getKey();

        // Save Data in Firebase
        Map<String, Object> ticketData = new HashMap<>();
        ticketData.put("eventDate", selectedDate);
        ticketData.put("ticketCount", selectedTickets);
        ticketData.put("seatType", selectedSeatType);

        if (ticketId != null) {
            userTicketsRef.child(ticketId).setValue(ticketData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventViewActivity.this, "Ticket booked successfully!", Toast.LENGTH_SHORT).show();

                        // Pass ticket ID to TicketView
                        Intent intent = new Intent(EventViewActivity.this, TicketView.class);
                        intent.putExtra("ticketId", ticketId);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventViewActivity.this, "Booking failed!", Toast.LENGTH_SHORT).show());
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> datePicker.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }
}
