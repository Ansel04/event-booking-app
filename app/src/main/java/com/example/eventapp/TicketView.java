package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.adapters.TicketAdapter;
import com.example.eventapp.models.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TicketView extends AppCompatActivity {

    private RecyclerView ticketRecyclerView;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    private DatabaseReference userTicketsRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);

        ticketRecyclerView = findViewById(R.id.ticketView);
        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        ticketList = new ArrayList<>();
        ticketAdapter = new TicketAdapter(ticketList);
        ticketRecyclerView.setAdapter(ticketAdapter);

        if (currentUser != null) {
            String userId = currentUser.getUid();
            userTicketsRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Tickets");
            fetchTickets();
        } else {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTickets() {
        userTicketsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ticketList.clear();
                for (DataSnapshot ticketSnapshot : snapshot.getChildren()) {
                    Ticket ticket = ticketSnapshot.getValue(Ticket.class);
                    if (ticket != null) {
                        ticketList.add(ticket);
                    }
                }
                ticketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching tickets", error.toException());
            }
        });
    }
}
