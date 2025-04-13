package com.example.showsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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
        holder.titleTextView.setText(show.getTitle());
        holder.categoryTextView.setText(show.getCategory());
        holder.descriptionTextView.setText(show.getShortDescription());

        holder.itemView.setOnClickListener(v -> listener.onShowClick(show));
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    static class ShowViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView categoryTextView;
        TextView descriptionTextView;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}