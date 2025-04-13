package com.example.showsapp;

import java.util.Date;

public class Show {
    private String id;
    private String title;
    private String category;
    private String shortDescription;
    private String fullDescription;
    private String imageUrl;
    private double price;
    private Date date;
    private String time;
    private String venue;
    private String duration;
    private int availableSeats;
    private boolean isPopular;

    // Constructor
    public Show(String id, String title, String category, String shortDescription,
                String fullDescription, String imageUrl, double price, Date date,
                String time, String venue, String duration, int availableSeats,
                boolean isPopular) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.imageUrl = imageUrl;
        this.price = price;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.duration = duration;
        this.availableSeats = availableSeats;
        this.isPopular = isPopular;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    // Helper method to get formatted price
    public String getFormattedPrice() {
        return String.format("$%.2f", price);
    }

    // Helper method to get date as string (you can customize the format)
    public String getFormattedDate() {
        // SimpleDateFormat would be better here, but keeping it simple for now
        return date.toString();
    }
}