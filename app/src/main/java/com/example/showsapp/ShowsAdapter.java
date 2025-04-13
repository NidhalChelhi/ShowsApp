    package com.example.showsapp;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;

    import java.text.SimpleDateFormat;
    import java.util.List;
    import java.util.Locale;

    public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowViewHolder> {
        private List<Show> showsList;
        private OnShowClickListener listener;

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

            // Load image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(show.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.showImageView);

            // Set text values
            holder.titleTextView.setText(show.getTitle());
            holder.categoryTextView.setText(show.getCategory());
            holder.descriptionTextView.setText(show.getShortDescription());
            holder.priceTextView.setText(show.getFormattedPrice());

            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.getDefault());
            holder.dateTextView.setText(dateFormat.format(show.getDate()));

            // Set time and seats
            holder.timeTextView.setText(show.getTime());
            holder.seatsTextView.setText(String.format(Locale.getDefault(), "%d seats", show.getAvailableSeats()));

            // Set click listener
            holder.itemView.setOnClickListener(v -> listener.onShowClick(show));
        }

        @Override
        public int getItemCount() {
            return showsList.size();
        }

        static class ShowViewHolder extends RecyclerView.ViewHolder {
            ImageView showImageView;
            TextView titleTextView;
            TextView categoryTextView;
            TextView descriptionTextView;
            TextView priceTextView;
            TextView dateTextView;
            TextView timeTextView;
            TextView seatsTextView;

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
            }
        }
    }