package com.example.showsapp;

public class Reservation {
    private String showId;
    private String fullName;
    private String email;
    private String phone;
    private double latitude;
    private double longitude;

    public Reservation(String showId, String fullName, String email,
                       String phone, double latitude, double longitude) {
        this.showId = showId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public String getShowId() { return showId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}