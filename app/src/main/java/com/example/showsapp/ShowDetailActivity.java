package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShowDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_show_detail);

        // Get the show ID from the intent
        String showId = getIntent().getStringExtra("SHOW_ID");

        // Find the show in our dummy data
        Show show = findShowById(showId);

        if (show != null) {
            // Initialize views
            ImageView showImage = findViewById(R.id.showImage);
            TextView titleTextView = findViewById(R.id.titleTextView);
            TextView categoryTextView = findViewById(R.id.categoryTextView);
            TextView priceTextView = findViewById(R.id.priceTextView);
            TextView dateTextView = findViewById(R.id.dateTextView);
            TextView timeTextView = findViewById(R.id.timeTextView);
            TextView venueTextView = findViewById(R.id.venueTextView);
            TextView durationTextView = findViewById(R.id.durationTextView);
            TextView seatsTextView = findViewById(R.id.seatsTextView);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            Button reserveButton = findViewById(R.id.reserveButton);

            // Load show image
            if (show.getImageUrl() != null && !show.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(show.getImageUrl())
                        .placeholder(R.color.surface)
                        .into(showImage);
            }

            // Set text values
            titleTextView.setText(show.getTitle());
            categoryTextView.setText(show.getCategory());
            priceTextView.setText(show.getFormattedPrice());

            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
            dateTextView.setText(dateFormat.format(show.getDate()));

            timeTextView.setText(show.getTime());
            venueTextView.setText(show.getVenue());
            durationTextView.setText(getString(R.string.duration_format, show.getDuration()));
            seatsTextView.setText(getString(R.string.seats_available_format, show.getAvailableSeats()));
            descriptionTextView.setText(show.getFullDescription());

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