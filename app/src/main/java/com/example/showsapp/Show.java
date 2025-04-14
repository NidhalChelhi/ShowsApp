package com.example.showsapp;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private String date;
    private String venue;

    @SerializedName("durationMinutes")
    private int durationMinutes;

    @SerializedName("availableSeats")
    private int availableSeats;

    @SerializedName("isPopular")
    private boolean isPopular;
    private double rating;

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public double getRating() {
        return rating;
    }

    // Helper methods
    public String getFormattedPrice() {
        return String.format(Locale.getDefault(), "$%.2f", price);
    }

    public String getFormattedRating() {
        return String.format(Locale.getDefault(), "%.1f", rating);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDurationString() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return String.format(Locale.getDefault(), "%d %s %d %s", hours, hours == 1 ? "hour" : "hours", minutes, minutes == 1 ? "minute" : "minutes");
    }

    public Date getParsedDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}