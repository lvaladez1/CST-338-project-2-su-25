package com.example.cst_338_project_2_su_25.viewHolders;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.ReviewsActivity;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    public ReviewAdapter(List<Review> reviews) {
        this.reviewList = reviews;
    }

    private List<Review> reviewList;

    public void setReviews(List<Review> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);

        // Bind currentReview so onClick has access to it
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Review currentReview;
        TextView title, type, rating, text, fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.reviewTitle);
            type = itemView.findViewById(R.id.mediaType);
            rating = itemView.findViewById(R.id.rating);
            text = itemView.findViewById(R.id.reviewText);
            fav = itemView.findViewById(R.id.fav);

            itemView.setOnLongClickListener(view -> {
                if (currentReview != null) {
                    Intent intent = new Intent(view.getContext(), ReviewsActivity.class);

                    intent.putExtra("reviewId", currentReview.getReviewId());
                    intent.putExtra("userId", currentReview.getUserId());
                    intent.putExtra("reviewTitle", currentReview.getTitle());
                    intent.putExtra("rating", currentReview.getRating());
                    intent.putExtra("type", currentReview.getType());
                    intent.putExtra("reviewText", currentReview.getReviewText());
                    intent.putExtra("isFavorite", currentReview.isFavorite());

                    view.getContext().startActivity(intent);
                }
                return false;
            });
        }

        public void bind(Review review) {
            currentReview = review;

            // Sets the fields here to display on Review History Activity.
            title.setText(review.title);
            type.setText(review.type);
            rating.setText(String.valueOf("Rating: " + review.rating));
            text.setText(review.reviewText);
            fav.setText(review.isFavorite ? "Favorite" : null );
        }
    }
}