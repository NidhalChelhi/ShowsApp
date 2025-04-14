package com.example.showsapp;

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
            // Check all conditions
            if (matchesSearch(show, query) &&
                    matchesCategory(show, categories) &&
                    show.getPrice() <= maxPrice &&
                    show.getRating() >= minRating &&
                    matchesUpcoming(show, upcomingOnly, currentTime) &&
                    show.getDurationMinutes() <= maxDuration &&
                    (!popularOnly || show.isPopular()) &&
                    (!availableSeatsOnly || show.getAvailableSeats() > 0)) {
                filteredShows.add(show);
            }
        }
        return filteredShows;
    }

    private static boolean matchesSearch(Show show, String query) {
        if (query == null || query.isEmpty()) return true;

        String lowerQuery = query.toLowerCase(Locale.getDefault());
        return show.getTitle().toLowerCase(Locale.getDefault()).contains(lowerQuery) ||
                show.getShortDescription().toLowerCase(Locale.getDefault()).contains(lowerQuery);
    }

    private static boolean matchesCategory(Show show, List<String> categories) {
        return categories == null || categories.isEmpty() ||
                categories.contains(show.getCategory());
    }

    private static boolean matchesUpcoming(Show show, boolean upcomingOnly, long currentTime) {
        if (!upcomingOnly) return true;

        Date showDate = show.getParsedDate();
        return showDate != null && showDate.getTime() > currentTime;
    }

    public static List<Show> simpleFilter(
            List<Show> allShows,
            String query,
            List<String> categories
    ) {
        return filterShows(allShows, query, categories,
                Integer.MAX_VALUE, 0, false,
                Integer.MAX_VALUE, false, false);
    }
}