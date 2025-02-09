package com.example.eventapp;

import static com.example.eventapp.R.id.popular_events_recyclerview;
import static com.example.eventapp.R.id.category_events_recyclerview;
import static com.example.eventapp.R.id.upcoming_events_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.eventapp.adapters.MusicAdapter;
import com.example.eventapp.adapters.PopularAdapter;
import com.example.eventapp.adapters.SportsAdapter;
import com.example.eventapp.models.MusicModel;
import com.example.eventapp.models.PopularModel;
import com.example.eventapp.models.SportsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {

    FirebaseAuth auth;
    ImageButton navhome, navfeed, navticket, navprofile;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("TicketPrefs", MODE_PRIVATE);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(Homepage.this, MainActivity.class));
            finish();
        }

        navhome = findViewById(R.id.navhome);
        navfeed = findViewById(R.id.navfeed);
        navticket = findViewById(R.id.navticket);
        navprofile = findViewById(R.id.navprofile);

        RecyclerView recyclerView = findViewById(popular_events_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<PopularModel> itemList = new ArrayList<>();
        itemList.add(new PopularModel("Coldplay", R.drawable.baseline_account_circle_24));
        itemList.add(new PopularModel("Martin Garrix", R.drawable.game));
        itemList.add(new PopularModel("Lollapalooza", R.drawable.music));
        itemList.add(new PopularModel("F1", R.drawable.theater));

        PopularAdapter adapter = new PopularAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        RecyclerView categoryRecyclerView = findViewById(category_events_recyclerview);
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);

        List<MusicModel> categoryItemList = new ArrayList<>();
        categoryItemList.add(new MusicModel("Coldplay", R.drawable.game));
        categoryItemList.add(new MusicModel("Hurry Up Tommorow", R.drawable.cultural));
        categoryItemList.add(new MusicModel("Carnival", R.drawable.music));
        categoryItemList.add(new MusicModel("Utopia", R.drawable.lock));

        MusicAdapter musicAdapter = new MusicAdapter(this, categoryItemList);
        categoryRecyclerView.setAdapter(musicAdapter);

        RecyclerView upcomingRecyclerView = findViewById(upcoming_events_recyclerview);
        LinearLayoutManager upcomingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        upcomingRecyclerView.setLayoutManager(upcomingLayoutManager);

        List<SportsModel> upcomingItemList = new ArrayList<>();
        upcomingItemList.add(new SportsModel("Football", R.drawable.game));
        upcomingItemList.add(new SportsModel("Table Tennis", R.drawable.cultural));
        upcomingItemList.add(new SportsModel("F1", R.drawable.music));
        upcomingItemList.add(new SportsModel("Cricket", R.drawable.lock));

        SportsAdapter sportsAdapter = new SportsAdapter(this, upcomingItemList);
        upcomingRecyclerView.setAdapter(sportsAdapter);

        navfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed = new Intent(Homepage.this, Feedback.class);
                startActivity(feed);
            }
        });

        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(Homepage.this, Profile.class);
                startActivity(profile);
            }
        });

        navticket.setOnClickListener(view -> {
            String lastTicketId = sharedPreferences.getString("lastTicketId", null);
            if (lastTicketId != null) {
                Intent intent = new Intent(Homepage.this, TicketView.class);
                intent.putExtra("ticketId", lastTicketId);
                startActivity(intent);
            } else {
                Toast.makeText(Homepage.this, "No booked ticket found!", Toast.LENGTH_SHORT).show();
            }
        });

        navprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
