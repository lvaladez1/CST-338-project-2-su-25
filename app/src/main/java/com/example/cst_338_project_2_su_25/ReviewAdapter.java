package com.example.cst_338_project_2_su_25;

import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<MediaTitle> mediaList;

    public ReviewAdapter(List<MediaTitle> mediaList) {
        this.mediaList = mediaList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, ratingTextView;
        Button favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recyclerItemTextView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_title_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaTitle media = mediaList.get(position);
        holder.titleTextView.setText(media.getTitle());

        holder.favoriteButton.setOnClickListener(v -> {
            Favorites favorite = new Favorites();

            Context context = holder.itemView.getContext();
            int currentUserId = context
                    .getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
                    .getInt("userId", -1);
            favorite.userId = currentUserId;

            favorite.mediaTitleId = media.getMediaTitleId();

            Log.d("ReviewAdapter", "Inserting favorite: userId="
                    + favorite.userId
                    + " mediaTitleId="
                    + favorite.mediaTitleId);

            new Thread(() -> {
                RevuDatabase.getDatabase(holder.itemView.getContext()).favoritesDAO().addFavorite(favorite);
            }).start();

            Toast.makeText(holder.itemView.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}