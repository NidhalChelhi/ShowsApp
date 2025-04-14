package com.example.showsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPhone, etSeats;
    private String showId, showTitle;
    private int availableSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Make Reservation");
        }

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSeats = findViewById(R.id.etSeats);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        TextView tvShowTitle = findViewById(R.id.showTitle);

        // Get show details from intent
        showId = getIntent().getStringExtra("SHOW_ID");
        showTitle = getIntent().getStringExtra("SHOW_TITLE");
        availableSeats = getIntent().getIntExtra("AVAILABLE_SEATS", 0);

        TextInputLayout seatsLayout = (TextInputLayout) findViewById(R.id.etSeats).getParent().getParent();
        seatsLayout.setHelperText("Available seats: " + availableSeats);

        if (showTitle != null) {
            tvShowTitle.setText("Reservation for: " + showTitle);
        }

        btnSubmit.setOnClickListener(v -> submitReservation());
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

    private void submitReservation() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String seatsStr = etSeats.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            return;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Valid email is required");
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Phone number is required");
            return;
        }

        if (seatsStr.isEmpty()) {
            etSeats.setError("Number of seats is required");
            return;
        }

        int numberOfSeats = Integer.parseInt(seatsStr);
        if (numberOfSeats <= 0) {
            etSeats.setError("Must reserve at least 1 seat");
            return;
        }

        if (numberOfSeats > availableSeats) {
            etSeats.setError("Only " + availableSeats + " seats available");
            return;
        }

        ReservationRequest request = new ReservationRequest(
                showId,
                fullName,
                email,
                phone,
                numberOfSeats
        );

        ShowApiService apiService = ApiClient.getClient().create(ShowApiService.class);
        Call<Reservation> call = apiService.createReservation(request);

        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {
                    // Update the local show data
                    updateLocalShowData(numberOfSeats);

                    Toast.makeText(ReservationActivity.this,
                            "Reservation successful for " + numberOfSeats + " seats!",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ReservationActivity.this,
                            "Reservation failed: " + response.message(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Toast.makeText(ReservationActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateLocalShowData(int reservedSeats) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("RESERVED_SEATS", reservedSeats);
        resultIntent.putExtra("SHOW_ID", showId);
        setResult(RESULT_OK, resultIntent);
    }
}