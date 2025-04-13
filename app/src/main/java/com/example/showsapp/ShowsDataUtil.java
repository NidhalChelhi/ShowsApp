// ShowsDataUtil.java
package com.example.showsapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowsDataUtil {
    public static List<Show> generateDummyShows() {
        List<Show> shows = new ArrayList<>();

        // Add some dummy shows
        shows.add(new Show(
                "1",
                "Jazz Night Extravaganza",
                "Music",
                "An evening of smooth jazz with international artists",
                "Join us for an unforgettable evening of jazz music featuring world-renowned artists. " +
                        "This special event will showcase a blend of traditional and contemporary jazz styles. " +
                        "The performance will include solo acts and ensemble pieces, with a special " +
                        "appearance by Grammy-winning saxophonist John Doe.",
                "https://example.com/images/jazz-night.jpg",
                45.99,
                new Date(System.currentTimeMillis() + 86400000 * 7), // 7 days from now
                "19:30",
                "Grand Concert Hall",
                "2 hours 30 minutes",
                150,
                true
        ));

        shows.add(new Show(
                "2",
                "Contemporary Dance Fusion",
                "Dance",
                "Modern dance performance by award-winning troupe",
                "Experience the cutting edge of contemporary dance with this innovative performance " +
                        "by the critically acclaimed Motion Dance Collective. Their unique blend of " +
                        "ballet, modern, and street dance styles creates a mesmerizing visual spectacle " +
                        "that tells a story of urban life and human connection.",
                "https://example.com/images/dance-fusion.jpg",
                39.99,
                new Date(System.currentTimeMillis() + 86400000 * 10), // 10 days from now
                "20:00",
                "City Theater",
                "1 hour 45 minutes",
                85,
                false
        ));

        shows.add(new Show(
                "3",
                "Comedy Club Special",
                "Comedy",
                "Stand-up night featuring top comedians",
                "Laugh the night away with our special comedy showcase featuring five of the hottest " +
                        "stand-up comedians on the circuit today. From observational humor to satirical " +
                        "commentary, this diverse lineup has something for everyone. Hosted by local " +
                        "favorite Jane Smith with special guest Mike Johnson from TV's 'Laugh Factory'.",
                "https://example.com/images/comedy-club.jpg",
                29.99,
                new Date(System.currentTimeMillis() + 86400000 * 3), // 3 days from now
                "21:00",
                "Downtown Comedy Cellar",
                "2 hours",
                50,
                true
        ));

        // Add more shows as needed
        shows.add(new Show(
                "4",
                "Classical Symphony",
                "Music",
                "Orchestral performance of Beethoven's greatest works",
                "The City Philharmonic Orchestra presents a special performance of Beethoven's most " +
                        "celebrated symphonies, conducted by Maestro Robert Chen. This program includes " +
                        "Symphony No. 5 and Symphony No. 9 ('Choral') with a full choir for the final movement.",
                "https://example.com/images/symphony.jpg",
                59.99,
                new Date(System.currentTimeMillis() + 86400000 * 14), // 14 days from now
                "19:00",
                "Symphony Hall",
                "2 hours 15 minutes",
                200,
                true
        ));

        shows.add(new Show(
                "5",
                "Magic & Illusion",
                "Performance",
                "Mind-bending magic show for all ages",
                "International magician David Wonder presents his award-winning show of illusions " +
                        "and mentalism. This family-friendly performance includes disappearing acts, " +
                        "levitation, and audience participation segments that will leave you questioning reality.",
                "https://example.com/images/magic-show.jpg",
                34.99,
                new Date(System.currentTimeMillis() + 86400000 * 5), // 5 days from now
                "18:30",
                "Majestic Theater",
                "1 hour 30 minutes",
                120,
                false
        ));

        return shows;
    }
}