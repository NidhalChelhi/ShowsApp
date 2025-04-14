package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailActivity extends AppCompatActivity {

    private ShowApiService apiService;
    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Show Details");
        }

        // Initialize API service
        apiService = ApiClient.getClient().create(ShowApiService.class);

        // Get the show ID from the intent
        String showId = getIntent().getStringExtra("SHOW_ID");
        if (showId != null) {
            fetchShowDetails(showId);
        } else {
            Toast.makeText(this, "Show not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchShowDetails(String showId) {
        Call<Show> call = apiService.getShowById(showId);
        call.enqueue(new Callback<Show>() {
            @Override
            public void onResponse(Call<Show> call, Response<Show> response) {
                if (response.isSuccessful() && response.body() != null) {
                    show = response.body();
                    populateShowDetails(show);
                } else {
                    Toast.makeText(ShowDetailActivity.this, "Failed to load show details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Show> call, Throwable t) {
                Toast.makeText(ShowDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void populateShowDetails(Show show) {
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
                .error(R.drawable.placeholder_image)
                .into(showImage);

        // Set text values with proper styling
        titleTextView.setText(show.getTitle());
        categoryTextView.setText(show.getCategory());
        priceTextView.setText(show.getFormattedPrice());
        ratingTextView.setText(show.getFormattedRating());
        venueTextView.setText(show.getVenue());
        durationTextView.setText(String.format("Duration: %s", show.getDurationString()));
        seatsTextView.setText(String.format("Seats available: %d", show.getAvailableSeats()));
        descriptionTextView.setText(show.getFullDescription());

        // Format date and time from ISO string
        try {
            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = apiFormat.parse(show.getDate());

            if (date != null) {
                // Format date as "EEE, MMM d, yyyy"
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
                dateTextView.setText(dateFormat.format(date));

                // Format time as "h:mm a"
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                timeTextView.setText(timeFormat.format(date));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            dateTextView.setText("Date not available");
            timeTextView.setText("Time not available");
        }

        // Set up reserve button
        reserveButton.setOnClickListener(v -> {
            if (show.getAvailableSeats() > 0) {
                Intent intent = new Intent(this, ReservationActivity.class);
                intent.putExtra("SHOW_ID", show.getId());
                intent.putExtra("SHOW_TITLE", show.getTitle());
                intent.putExtra("AVAILABLE_SEATS", show.getAvailableSeats());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No seats available for this show", Toast.LENGTH_SHORT).show();
            }
        });
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
}