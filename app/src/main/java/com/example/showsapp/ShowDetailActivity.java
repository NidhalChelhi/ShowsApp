package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShowDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        // Initialize toolbar - ADD THIS
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Show Details");
        }

        // Get the show ID from the intent
        String showId = getIntent().getStringExtra("SHOW_ID");
        Show show = findShowById(showId);

        if (show != null) {
            // Initialize views
            ImageView showImage = findViewById(R.id.showImage);
            TextView titleTextView = findViewById(R.id.titleTextView);
            TextView categoryTextView = findViewById(R.id.categoryTextView);
            TextView priceTextView = findViewById(R.id.priceTextView);
            TextView ratingTextView = findViewById(R.id.ratingTextView);
            TextView dateTextView = findViewById(R.id.dateTextView);
            TextView timeTextView = findViewById(R.id.timeTextView);
            TextView venueTextView = findViewById(R.id.venueTextView);
            TextView durationTextView = findViewById(R.id.durationTextView);
            TextView seatsTextView = findViewById(R.id.seatsTextView);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            Button reserveButton = findViewById(R.id.reserveButton);

            // Load show image
            Glide.with(this)
                    .load(show.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)  // Add an error placeholder
                    .into(showImage);
            // Set text values with proper styling
            titleTextView.setText(show.getTitle());
            titleTextView.setTextSize(24);
            titleTextView.setTextColor(getResources().getColor(R.color.black));

            categoryTextView.setText(show.getCategory());
            categoryTextView.setTextSize(14);
            categoryTextView.setBackgroundResource(R.drawable.bg_category_chip);
            categoryTextView.setTextColor(getResources().getColor(R.color.white));

            priceTextView.setText(show.getFormattedPrice());
            priceTextView.setTextSize(20);
            priceTextView.setTextColor(getResources().getColor(R.color.purple_500));

            ratingTextView.setText(show.getFormattedRating());


            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
            dateTextView.setText(dateFormat.format(show.getDate()));
            dateTextView.setTextSize(16);

            timeTextView.setText(show.getTime());
            timeTextView.setTextSize(16);

            venueTextView.setText(show.getVenue());
            venueTextView.setTextSize(16);

            durationTextView.setText(String.format("Duration: %s", show.getDuration()));
            durationTextView.setTextSize(16);

            seatsTextView.setText(String.format("Seats available: %d", show.getAvailableSeats()));
            seatsTextView.setTextSize(16);

            descriptionTextView.setText(show.getFullDescription());
            descriptionTextView.setTextSize(16);

            // Set up reserve button
            reserveButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, ReservationActivity.class);
                intent.putExtra("SHOW_ID", show.getId());
                intent.putExtra("SHOW_TITLE", show.getTitle());
                intent.putExtra("AVAILABLE_SEATS", show.getAvailableSeats());
                startActivity(intent);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Show findShowById(String id) {
        List<Show> shows = ShowsDataUtil.generateDummyShows();
        for (Show show : shows) {
            if (show.getId().equals(id)) {
                return show;
            }
        }
        return null;
    }
}