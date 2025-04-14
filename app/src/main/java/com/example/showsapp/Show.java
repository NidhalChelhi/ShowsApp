package com.example.showsapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Show {
    private String id;
    private String title;
    private String category;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("fullDescription")
    private String fullDescription;

    @SerializedName("imageUrl")
    private String imageUrl;
    private double price;
    private String date;  // Changed from Date to String
    private String venue;

    @SerializedName("durationMinutes")
    private int durationMinutes;  // Changed from String duration to int

    @SerializedName("availableSeats")
    private int availableSeats;

    @SerializedName("isPopular")
    private boolean isPopular;
    private double rating;

    // Constructor
    public Show(String id, String title, String category, String shortDescription,
                String fullDescription, String imageUrl, double price, String date,
                String venue, int durationMinutes, int availableSeats,
                boolean isPopular, double rating) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.imageUrl = imageUrl;
        this.price = price;
        this.date = date;
        this.venue = venue;
        this.durationMinutes = durationMinutes;
        this.availableSeats = availableSeats;
        this.isPopular = isPopular;
        this.rating = rating;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getShortDescription() { return shortDescription; }
    public String getFullDescription() { return fullDescription; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getAvailableSeats() { return availableSeats; }
    public boolean isPopular() { return isPopular; }
    public double getRating() { return rating; }

    // Helper methods
    public String getFormattedPrice() {
        return String.format("$%.2f", price);
    }

    public String getFormattedRating() {
        return String.format("%.1f", rating);
    }

    public String getDurationString() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return hours + " hours " + minutes + " minutes";
    }
}