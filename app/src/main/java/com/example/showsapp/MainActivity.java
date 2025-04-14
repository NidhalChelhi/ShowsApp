package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ShowsAdapter.OnShowClickListener {
    private RecyclerView showsRecyclerView;
    private ShowsAdapter showsAdapter;
    private List<Show> allShows = new ArrayList<>();
    private SearchView searchView;
    private ShowApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiService = ApiClient.getClient().create(ShowApiService.class);

        showsRecyclerView = findViewById(R.id.showsRecyclerView);
        showsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        showsAdapter = new ShowsAdapter(allShows, this);
        showsRecyclerView.setAdapter(showsAdapter);

        fetchShowsFromApi();
    }

    private void fetchShowsFromApi() {
        Call<List<Show>> call = apiService.getAllShows();
        call.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allShows = response.body();
                    showsAdapter.updateList(allShows);

                    // Log the response for debugging
                    Log.d("API_RESPONSE", "Number of shows fetched: " + allShows.size());
                    for (Show show : allShows) {
                        Log.d("API_RESPONSE", "Show: " + show.getTitle() + ", Date: " + show.getDate());
                    }
                } else {
                    String errorMessage = "Failed to fetch shows: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += " - " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("API_ERROR", errorMessage);
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {
                String errorMessage = "Error: " + t.getMessage();
                Log.e("API_FAILURE", errorMessage, t);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

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
                List<Show> filtered = ShowFilters.simpleFilter(allShows, newText, null);
                showsAdapter.updateList(filtered);
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

        ChipGroup categoryChipGroup = dialogView.findViewById(R.id.categoryChipGroup);
        SeekBar priceSeekBar = dialogView.findViewById(R.id.priceSeekBar);
        TextView priceRangeText = dialogView.findViewById(R.id.priceRangeText);
        RatingBar ratingFilter = dialogView.findViewById(R.id.ratingFilter);
        Switch upcomingOnlySwitch = dialogView.findViewById(R.id.upcomingOnlySwitch);
        SeekBar durationSeekBar = dialogView.findViewById(R.id.durationSeekBar);
        TextView durationText = dialogView.findViewById(R.id.durationText);
        Switch popularOnlySwitch = dialogView.findViewById(R.id.popularOnlySwitch);
        Switch availableSeatsSwitch = dialogView.findViewById(R.id.availableSeatsSwitch);

        priceSeekBar.setMax(100);
        priceSeekBar.setProgress(100);
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceRangeText.setText("Max Price: $" + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        durationSeekBar.setMax(240);
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
            List<String> selectedCategories = new ArrayList<>();
            for (int i = 0; i < categoryChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) categoryChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    selectedCategories.add(chip.getText().toString());
                }
            }

            List<Show> filtered = ShowFilters.filterShows(
                    allShows,
                    searchView != null ? searchView.getQuery().toString() : "",
                    selectedCategories.isEmpty() ? null : selectedCategories,
                    priceSeekBar.getProgress(),
                    ratingFilter.getRating(),
                    upcomingOnlySwitch.isChecked(),
                    durationSeekBar.getProgress(),
                    popularOnlySwitch.isChecked(),
                    availableSeatsSwitch.isChecked()
            );

            showsAdapter.updateList(filtered);
        });

        builder.setNegativeButton("Clear", (dialog, which) -> {
            if (searchView != null) {
                searchView.setQuery("", false);
            }
            showsAdapter.updateList(allShows);
        });

        builder.setNeutralButton("Cancel", null);
        builder.create().show();
    }

    @Override
    public void onShowClick(Show show) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra("SHOW_ID", show.getId());
        startActivity(intent);
    }
}