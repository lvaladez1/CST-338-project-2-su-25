package com.example.cst_338_project_2_su_25.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.entities.FavoriteDisplay;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<FavoriteDisplay> favoriteList= new ArrayList<>();

    public FavoritesAdapter() {
    }

    public void setFavorites(List<FavoriteDisplay> newFavorites) {
        favoriteList.clear();
        if (newFavorites != null) {
            favoriteList.addAll(newFavorites);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, usernameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.favTitleTextView);
            usernameTextView = itemView.findViewById(R.id.favUsernameTextView);
        }
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteDisplay fav = favoriteList.get(position);
        holder.titleTextView.setText(fav.title);
        holder.usernameTextView.setText(fav.username);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}
