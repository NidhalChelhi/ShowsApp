package com.example.showsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowViewHolder> {
    private List<Show> showsList;
    private final OnShowClickListener listener;

    private static final int MAX_TITLE_LINES = 1;
    private static final int MAX_DESC_LINES = 2;
    private static final String DATE_FORMAT = "EEE, MMM d";
    private static final String TIME_FORMAT = "h:mm a";

    public interface OnShowClickListener {
        void onShowClick(Show show);
    }

    public ShowsAdapter(List<Show> showsList, OnShowClickListener listener) {
        this.showsList = showsList;
        this.listener = listener;
    }

    public void updateList(List<Show> newList) {
        showsList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Show show = showsList.get(position);
        holder.bind(show, listener);
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    static class ShowViewHolder extends RecyclerView.ViewHolder {
        private final ImageView showImageView;
        private final TextView titleTextView;
        private final TextView categoryTextView;
        private final TextView descriptionTextView;
        private final TextView priceTextView;
        private final TextView dateTextView;
        private final TextView timeTextView;
        private final TextView seatsTextView;
        private final TextView ratingTextView;
        private final TextView venueTextView;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            showImageView = itemView.findViewById(R.id.showImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            seatsTextView = itemView.findViewById(R.id.seatsTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            venueTextView = itemView.findViewById(R.id.venueTextView);
        }

        public void bind(Show show, OnShowClickListener listener) {
            loadImage(show);
            setTextViews(show);
            itemView.setOnClickListener(v -> listener.onShowClick(show));
        }

        private void loadImage(Show show) {
            Glide.with(itemView.getContext())
                    .load(show.getImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image))
                    .into(showImageView);
        }

        private void setTextViews(Show show) {
            titleTextView.setText(show.getTitle());
            categoryTextView.setText(show.getCategory());
            descriptionTextView.setText(show.getShortDescription());
            priceTextView.setText(show.getFormattedPrice());
            ratingTextView.setText(show.getFormattedRating());
            venueTextView.setText(show.getVenue());
            seatsTextView.setText(itemView.getContext().getString(
                    R.string.seats_available, show.getAvailableSeats()));

            Date showDate = show.getParsedDate();
            if (showDate != null) {
                dateTextView.setText(new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(showDate));
                timeTextView.setText(new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(showDate));
            } else {
                dateTextView.setText(R.string.date_not_available);
                timeTextView.setText(R.string.time_not_available);
            }
        }
    }
}