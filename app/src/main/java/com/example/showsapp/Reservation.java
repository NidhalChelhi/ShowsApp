package com.example.showsapp;

public class Reservation {
    private String showId;
    private String fullName;
    private String email;
    private String phone;
    private int numberOfSeats;

    public Reservation(String showId, String fullName, String email,
                       String phone, int numberOfSeats) {
        this.showId = showId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.numberOfSeats = numberOfSeats;
    }

    // Getters
    public String getShowId() { return showId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getNumberOfSeats() { return numberOfSeats; }
}