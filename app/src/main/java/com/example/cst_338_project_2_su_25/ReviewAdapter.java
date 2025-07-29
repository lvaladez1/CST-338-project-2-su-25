package com.example.cst_338_project_2_su_25;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<Review> reviewList;

    public ReviewAdapter(List<Review> reviews) {
        this.reviewList = reviews;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, rating, text, fav;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            text = itemView.findViewById(R.id.reviewText);
            fav = itemView.findViewById(R.id.fav);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.title.setText(review.title);
        holder.rating.setText(String.valueOf(review.rating));
        holder.text.setText(review.reviewText);
        holder.fav.setText(review.isFavorite ? "Favorite" : "Not Favorite");
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}