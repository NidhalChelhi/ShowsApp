package com.example.showsapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowsDataUtil {
    public static List<Show> generateDummyShows() {
        List<Show> shows = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        long dayInMillis = 86400000;

        // Music Shows
        shows.add(new Show("1", "Jazz Night Extravaganza", "Music",
                "An evening of smooth jazz with international artists",
                "Join us for an unforgettable evening featuring world-class jazz musicians performing classic and contemporary jazz pieces in an intimate setting.",
                "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                45.99,
                new Date(currentTime + dayInMillis * 7), "19:30",
                "Grand Concert Hall", "2 hours 30 minutes", 150, true, 4.8));

        shows.add(new Show("2", "Rock Legends Live", "Music",
                "Greatest rock hits performed by legendary artists",
                "Relive the golden age of rock with this spectacular show featuring iconic musicians performing their greatest hits from the 70s and 80s.",
                "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                59.99,
                new Date(currentTime + dayInMillis * 3), "20:00",
                "Arena Stadium", "3 hours", 5000, true, 4.9));

        // Dance Shows
        shows.add(new Show("3", "Contemporary Dance Fusion", "Dance",
                "Modern dance performance by award-winning troupe",
                "Experience cutting-edge contemporary dance as this internationally acclaimed company blends modern techniques with traditional movements.",
                "https://images.unsplash.com/photo-1547153760-18fc86324498?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1471&q=80",
                39.99,
                new Date(currentTime + dayInMillis * 10), "20:00",
                "City Theater", "1 hour 45 minutes", 85, false, 4.2));

        shows.add(new Show("4", "Ballet Classics", "Dance",
                "Timeless ballet masterpieces",
                "Enjoy the most beautiful ballet performances from Swan Lake to The Nutcracker, performed by principal dancers from world-renowned companies.",
                "https://images.unsplash.com/photo-1543946207-39bd91e70ca7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
                49.99,
                new Date(currentTime - dayInMillis * 2), "19:00",
                "Royal Opera House", "2 hours 15 minutes", 200, true, 4.7));

        // Theater Shows
        shows.add(new Show("5", "Hamlet", "Theater",
                "Shakespeare's classic tragedy",
                "A modern interpretation of Shakespeare's masterpiece featuring an all-star cast and innovative staging that brings new life to this timeless story.",
                "https://images.unsplash.com/photo-1542300050-17aace4d9532?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                35.99,
                new Date(currentTime + dayInMillis * 5), "19:30",
                "Globe Theater", "2 hours 45 minutes", 300, false, 4.5));

        shows.add(new Show("6", "The Phantom of the Opera", "Theater",
                "Andrew Lloyd Webber's musical masterpiece",
                "The longest-running Broadway musical comes to your city with its spectacular sets, breathtaking costumes, and unforgettable music.",
                "https://images.unsplash.com/photo-1531058020387-3be344556be6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                79.99,
                new Date(currentTime + dayInMillis * 14), "20:00",
                "Majestic Theater", "2 hours 30 minutes", 1200, true, 4.9));

        // Comedy Shows
        shows.add(new Show("7", "Stand-Up Comedy Night", "Comedy",
                "Top comedians from across the country",
                "Laugh the night away with hilarious stand-up routines from today's hottest comedians, featuring both established stars and rising talents.",
                "https://images.unsplash.com/photo-1551818255-e6e10975bc17?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1465&q=80",
                29.99,
                new Date(currentTime + dayInMillis * 2), "21:00",
                "Comedy Club", "2 hours", 150, false, 4.0));

        // Family Shows
        shows.add(new Show("8", "Disney on Ice", "Family",
                "Magical Disney characters on ice",
                "Bring your kids to this magical ice skating show featuring their favorite Disney characters in a spectacular production with stunning special effects.",
                "https://images.unsplash.com/photo-1574267432553-4b4628081c31?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1631&q=80",
                44.99,
                new Date(currentTime + dayInMillis * 9), "15:00",
                "Ice Arena", "1 hour 30 minutes", 3000, true, 4.6));

        // Additional Shows
        shows.add(new Show("9", "Broadway Hits Revue", "Theater",
                "The best of Broadway in one spectacular show",
                "Experience show-stopping numbers from the most beloved Broadway musicals performed by an ensemble of Broadway veterans.",
                "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                65.99,
                new Date(currentTime + dayInMillis * 12), "20:00",
                "Broadway Theater", "2 hours 15 minutes", 800, true, 4.8));

        shows.add(new Show("10", "Symphony Under the Stars", "Music",
                "Outdoor classical music experience",
                "The city's premier orchestra performs beloved classical pieces under the night sky at our beautiful outdoor amphitheater.",
                "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                49.99,
                new Date(currentTime + dayInMillis * 6), "19:00",
                "City Park Amphitheater", "2 hours", 1200, false, 4.7));

        return shows;
    }
}