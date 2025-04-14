package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ShowsAdapter.OnShowClickListener {
    private RecyclerView showsRecyclerView;
    private ShowsAdapter showsAdapter;
    private List<Show> allShows;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        showsRecyclerView = findViewById(R.id.showsRecyclerView);
        showsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get all shows
        allShows = ShowsDataUtil.generateDummyShows();

        // Set initial data
        showsAdapter = new ShowsAdapter(allShows, this);
        showsRecyclerView.setAdapter(showsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Setup search view
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterShows(newText, null);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            showFilterDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_advanced_filters, null);
        builder.setView(dialogView);

        // Initialize all filter views
        ChipGroup categoryChipGroup = dialogView.findViewById(R.id.categoryChipGroup);
        SeekBar priceSeekBar = dialogView.findViewById(R.id.priceSeekBar);
        TextView priceRangeText = dialogView.findViewById(R.id.priceRangeText);
        RatingBar ratingFilter = dialogView.findViewById(R.id.ratingFilter);
        Switch upcomingOnlySwitch = dialogView.findViewById(R.id.upcomingOnlySwitch);
        SeekBar durationSeekBar = dialogView.findViewById(R.id.durationSeekBar);
        TextView durationText = dialogView.findViewById(R.id.durationText);
        Switch popularOnlySwitch = dialogView.findViewById(R.id.popularOnlySwitch);
        Switch availableSeatsSwitch = dialogView.findViewById(R.id.availableSeatsSwitch);

        // Price filter setup
        priceSeekBar.setMax(100); // $100 max
        priceSeekBar.setProgress(100);
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceRangeText.setText("Max Price: $" + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Duration filter setup
        durationSeekBar.setMax(240); // 240 minutes (4 hours)
        durationSeekBar.setProgress(240);
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                durationText.setText("Max Duration: " + progress + " mins");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Populate categories
        Set<String> categories = new HashSet<>();
        for (Show show : allShows) {
            categories.add(show.getCategory());
        }
        for (String category : categories) {
            Chip chip = new Chip(this);
            chip.setText(category);
            chip.setCheckable(true);
            categoryChipGroup.addView(chip);
        }

        builder.setPositiveButton("Apply", (dialog, which) -> {
            // Get selected categories
            List<String> selectedCategories = new ArrayList<>();
            for (int i = 0; i < categoryChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) categoryChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    selectedCategories.add(chip.getText().toString());
                }
            }

            // Get other filter values
            int maxPrice = priceSeekBar.getProgress();
            float minRating = ratingFilter.getRating();
            boolean upcomingOnly = upcomingOnlySwitch.isChecked();
            int maxDuration = durationSeekBar.getProgress();
            boolean popularOnly = popularOnlySwitch.isChecked();
            boolean availableSeatsOnly = availableSeatsSwitch.isChecked();

            // Apply all filters
            applyAdvancedFilters(
                    searchView != null ? searchView.getQuery().toString() : "",
                    selectedCategories.isEmpty() ? null : selectedCategories,
                    maxPrice,
                    minRating,
                    upcomingOnly,
                    maxDuration,
                    popularOnly,
                    availableSeatsOnly
            );
        });

        builder.setNegativeButton("Clear", (dialog, which) -> clearAllFilters());
        builder.setNeutralButton("Cancel", null);
        builder.create().show();
    }

    private void applyAdvancedFilters(
            String query,
            List<String> categories,
            int maxPrice,
            float minRating,
            boolean upcomingOnly,
            int maxDuration,
            boolean popularOnly,
            boolean availableSeatsOnly
    ) {
        List<Show> filteredShows = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (Show show : allShows) {
            // Convert duration string to minutes (e.g., "2 hours 30 minutes" -> 150)
            int durationMinutes = convertDurationToMinutes(show.getDuration());

            boolean matchesSearch = query.isEmpty() ||
                    show.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    show.getShortDescription().toLowerCase().contains(query.toLowerCase());

            boolean matchesCategory = categories == null ||
                    categories.contains(show.getCategory());

            boolean matchesPrice = show.getPrice() <= maxPrice;
            boolean matchesRating = show.getRating() >= minRating;
            boolean matchesUpcoming = !upcomingOnly || show.getDate().getTime() > currentTime;
            boolean matchesDuration = durationMinutes <= maxDuration;
            boolean matchesPopular = !popularOnly || show.isPopular();
            boolean matchesAvailability = !availableSeatsOnly || show.getAvailableSeats() > 0;

            if (matchesSearch && matchesCategory && matchesPrice && matchesRating &&
                    matchesUpcoming && matchesDuration && matchesPopular && matchesAvailability) {
                filteredShows.add(show);
            }
        }

        showsAdapter.updateList(filteredShows);
    }

    private int convertDurationToMinutes(String duration) {
        try {
            int hours = 0;
            int minutes = 0;

            if (duration.contains("hour")) {
                String hoursStr = duration.substring(0, duration.indexOf("hour")).trim();
                hours = Integer.parseInt(hoursStr);
            }

            if (duration.contains("minute")) {
                String minsStr = duration.substring(duration.indexOf("hour") > 0 ?
                        duration.indexOf("hour") + 4 : 0, duration.indexOf("minute")).trim();
                minsStr = minsStr.replaceAll("[^0-9]", "").trim();
                minutes = Integer.parseInt(minsStr);
            }

            return hours * 60 + minutes;
        } catch (Exception e) {
            return 240; // Default to max if parsing fails
        }
    }

    private void clearAllFilters() {
        if (searchView != null) {
            searchView.setQuery("", false);
        }
        showsAdapter.updateList(allShows);
    }

    private void filterShows(String query, List<String> categories) {
        List<Show> filteredShows = new ArrayList<>();

        for (Show show : allShows) {
            boolean matchesSearch = query.isEmpty() ||
                    show.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    show.getShortDescription().toLowerCase().contains(query.toLowerCase());

            boolean matchesCategory = categories == null ||
                    categories.contains(show.getCategory());

            if (matchesSearch && matchesCategory) {
                filteredShows.add(show);
            }
        }

        showsAdapter.updateList(filteredShows);
    }

    @Override
    public void onShowClick(Show show) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra("SHOW_ID", show.getId());
        startActivity(intent);
    }
}