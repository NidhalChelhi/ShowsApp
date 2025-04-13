package com.example.showsapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowsDataUtil {
    public static List<Show> generateDummyShows() {
        List<Show> shows = new ArrayList<>();

        shows.add(new Show(
                "1",
                "Jazz Night Extravaganza",
                "Music",
                "An evening of smooth jazz with international artists",
                "Join us for an unforgettable evening of jazz music...",
                "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                45.99,
                new Date(System.currentTimeMillis() + 86400000 * 7),
                "19:30",
                "Grand Concert Hall",
                "2 hours 30 minutes",
                150,
                true,
                3.8
        ));

        shows.add(new Show(
                "2",
                "Contemporary Dance Fusion",
                "Dance",
                "Modern dance performance by award-winning troupe",
                "Experience the cutting edge of contemporary dance...",
                "https://www.clistudios.com/wp-content/uploads/2021/08/jaquel-knight-hip-hop-scaled.jpeg",
                39.99,
                new Date(System.currentTimeMillis() + 86400000 * 10),
                "20:00",
                "City Theater",
                "1 hour 45 minutes",
                85,
                false,
                4.2
        ));

        return shows;
    }
}