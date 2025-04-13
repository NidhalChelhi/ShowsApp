package com.example.showsapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ReservationActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPhone, etSeats;
    private Button btnSubmit;
    private String showId, showTitle;
    private int availableSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSeats = findViewById(R.id.etSeats);
        btnSubmit = findViewById(R.id.btnSubmit);
        TextView tvShowTitle = findViewById(R.id.showTitle);

        // Get show details from intent
        showId = getIntent().getStringExtra("SHOW_ID");
        showTitle = getIntent().getStringExtra("SHOW_TITLE");
        availableSeats = getIntent().getIntExtra("AVAILABLE_SEATS", 0);

        if (showTitle != null) {
            tvShowTitle.setText("Reservation for: " + showTitle);
        }

        // Set up submit button
        btnSubmit.setOnClickListener(v -> submitReservation());
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
        // Validate inputs
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

        // Create reservation object
        Reservation reservation = new Reservation(
                showId,
                fullName,
                email,
                phone,
                numberOfSeats
        );

        // Show success message
        Toast.makeText(this, "Reservation successful for " + numberOfSeats + " seats!", Toast.LENGTH_LONG).show();
        finish();
    }
}