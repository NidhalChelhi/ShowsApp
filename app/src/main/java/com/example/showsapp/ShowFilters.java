package com.example.showsapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowFilters {

    public static List<Show> filterShows(
            List<Show> allShows,
            String query,
            List<String> categories,
            int maxPrice,
            float minRating,
            boolean upcomingOnly,
            int maxDuration,
            boolean popularOnly,
            boolean availableSeatsOnly
    ) {
        List<Show> filteredShows = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (Show show : allShows) {
            boolean matchesSearch = query.isEmpty() ||
                    show.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    show.getShortDescription().toLowerCase().contains(query.toLowerCase());

            boolean matchesCategory = categories == null || categories.isEmpty() ||
                    categories.contains(show.getCategory());

            boolean matchesPrice = show.getPrice() <= maxPrice;
            boolean matchesRating = show.getRating() >= minRating;
            boolean matchesDuration = show.getDurationMinutes() <= maxDuration;
            boolean matchesPopular = !popularOnly || show.isPopular();
            boolean matchesAvailability = !availableSeatsOnly || show.getAvailableSeats() > 0;

            // For upcomingOnly filter, we need to parse the date string
            boolean matchesUpcoming = true;
            if (upcomingOnly) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    Date showDate = sdf.parse(show.getDate());
                    matchesUpcoming = showDate != null && showDate.getTime() > currentTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                    matchesUpcoming = false;
                }
            }

            if (matchesSearch && matchesCategory && matchesPrice && matchesRating &&
                    matchesUpcoming && matchesDuration && matchesPopular && matchesAvailability) {
                filteredShows.add(show);
            }
        }

        return filteredShows;
    }
    public static List<Show> simpleFilter(
            List<Show> allShows,
            String query,
            List<String> categories
    ) {
        List<Show> filteredShows = new ArrayList<>();

        for (Show show : allShows) {
            boolean matchesSearch = query.isEmpty() ||
                    show.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    show.getShortDescription().toLowerCase().contains(query.toLowerCase());

            boolean matchesCategory = categories == null || categories.isEmpty() ||
                    categories.contains(show.getCategory());

            if (matchesSearch && matchesCategory) {
                filteredShows.add(show);
            }
        }

        return filteredShows;
    }

    private static int convertDurationToMinutes(String duration) {
        try {
            int hours = 0;
            int minutes = 0;

            if (duration.contains("hour")) {
                String hoursStr = duration.substring(0, duration.indexOf("hour")).trim();
                hours = Integer.parseInt(hoursStr);
            }

            if (duration.contains("minute")) {
                String minsStr = duration.substring(duration.indexOf("hour") > 0 ?
                        duration.indexOf("hour") + 4 : 0, duration.indexOf("minute")).trim();
                minsStr = minsStr.replaceAll("[^0-9]", "").trim();
                minutes = Integer.parseInt(minsStr);
            }

            return hours * 60 + minutes;
        } catch (Exception e) {
            return 240; // Default to max if parsing fails
        }
    }
}