package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

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
        Set<String> categories = new HashSet<>();
        for (Show show : allShows) {
            categories.add(show.getCategory());
        }

        String[] categoryArray = categories.toArray(new String[0]);
        boolean[] checkedItems = new boolean[categoryArray.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.filter_title)
                .setMultiChoiceItems(categoryArray, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        })
                .setPositiveButton("Apply", (dialog, which) -> {
                    List<String> selectedCategories = new ArrayList<>();
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            selectedCategories.add(categoryArray[i]);
                        }
                    }
                    String searchQuery = searchView != null ? searchView.getQuery().toString() : "";
                    filterShows(searchQuery, selectedCategories.isEmpty() ? null : selectedCategories);
                })
                .setNegativeButton("Clear", (dialog, which) -> {
                    String searchQuery = searchView != null ? searchView.getQuery().toString() : "";
                    filterShows(searchQuery, null);
                });

        builder.create().show();
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