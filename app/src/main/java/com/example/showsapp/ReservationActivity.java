package com.example.showsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

public class ReservationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private TextInputEditText etFullName, etEmail, etPhone, etLatitude, etLongitude;
    private Button btnGetLocation, btnSubmit;
    private String showId, showTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnSubmit = findViewById(R.id.btnSubmit);
        TextView tvShowTitle = findViewById(R.id.showTitle);

        // Get show details from intent
        showId = getIntent().getStringExtra("SHOW_ID");
        showTitle = getIntent().getStringExtra("SHOW_TITLE");
        if (showTitle != null) {
            tvShowTitle.setText("Reservation for: " + showTitle);
        }

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set up location button
        btnGetLocation.setOnClickListener(v -> requestLocation());

        // Set up submit button
        btnSubmit.setOnClickListener(v -> submitReservation());
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                etLatitude.setText(String.valueOf(location.getLatitude()));
                                etLongitude.setText(String.valueOf(location.getLongitude()));
                            } else {
                                Toast.makeText(ReservationActivity.this,
                                        "Unable to get current location",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this,
                        "Location permission is required to get coordinates",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitReservation() {
        // Validate inputs
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String latitude = etLatitude.getText().toString().trim();
        String longitude = etLongitude.getText().toString().trim();

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

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Location coordinates are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create reservation object (will be sent to API later)
        Reservation reservation = new Reservation(
                showId,
                fullName,
                email,
                phone,
                Double.parseDouble(latitude),
                Double.parseDouble(longitude)
        );

        // For now, just show success message
        Toast.makeText(this, "Reservation successful!", Toast.LENGTH_LONG).show();
        finish();
    }
}