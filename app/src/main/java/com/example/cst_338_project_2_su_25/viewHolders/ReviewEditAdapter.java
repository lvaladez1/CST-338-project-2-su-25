package com.example.cst_338_project_2_su_25.viewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewEditAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private final Context context;
    private final List<Review> reviews;

    public ReviewEditAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewItemView = LayoutInflater.from(context).inflate(R.layout.review_recycler_item, parent, false);
        return new ReviewViewHolder(reviewItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = reviews.get(position);
        holder.bind(currentReview);  // We’ll define this in the ViewHolder class next.

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
