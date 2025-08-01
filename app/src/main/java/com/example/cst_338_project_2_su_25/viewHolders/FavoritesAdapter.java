package com.example.cst_338_project_2_su_25.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.DisplayFavoritesActivity;
import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.MediaTitleDAO;
import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying a list of Favorites in a RecyclerView.
 * Each item displays the title of the favorite and the user ID associated with it.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<Favorites> favoriteList = new ArrayList<>();

    private FavoritesDAO favoritesDAO;
    Favorites fav;

    /**
     * Default constructor for FavoritesAdapter.
     */
    public FavoritesAdapter() {
    }

    /**
     * Sets the list of Favorites to be displayed and notifies the adapter to refresh the view.
     *
     * @param newFavorites A list of Favorites objects to display.
     */
    public void setFavorites(List<Favorites> newFavorites) {
        favoriteList.clear();
        if (newFavorites != null) {
            favoriteList.addAll(newFavorites);
        }
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that holds the view references for each list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, usernameTextView;

        /**
         * Constructs the ViewHolder and initializes the TextView fields.
         *
         * @param itemView The view representing the RecyclerView item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.favTitleTextView);
            usernameTextView = itemView.findViewById(R.id.favUsernameTextView);
        }
    }

    /**
     * Creates a new ViewHolder when the RecyclerView needs a new item layout to display.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The type of view to create.
     * @return A new ViewHolder instance.
     */
    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds data to the TextViews in the ViewHolder at the specified position.
     *
     * @param holder The ViewHolder instance to bind data to.
     * @param position The position in the list of data.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fav = favoriteList.get(position);
        if (fav != null) {
            holder.titleTextView.setText(fav.getTitle() != null ? fav.getTitle() : "No Title");
            String firstLetterOfName = String.valueOf(fav.getUserName()).substring(0, 1).toUpperCase();
            String remainderOfName = fav.getUserName().substring(1);
            holder.usernameTextView.setText("Reviewer: " + firstLetterOfName + remainderOfName);
        } else {
        holder.titleTextView.setText("This should be the title");
        holder.usernameTextView.setText("This should be the username.");
        }
    }

    /**
     * Returns the total number of items in the favorites list.
     *
     * @return The size of the list.
     */
    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}
