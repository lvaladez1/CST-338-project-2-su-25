package com.example.cst_338_project_2_su_25.viewHolders;



import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.ReviewsActivity;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView reviewTitleView;
    private final TextView reviewTextView;
    private Review currentReview;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        reviewTitleView = itemView.findViewById(R.id.reviewTitle);
        reviewTextView = itemView.findViewById(R.id.reviewText);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentReview != null) {
                    Intent intent = new Intent(view.getContext(), ReviewsActivity.class);
                    intent.putExtra("reviewTitle", currentReview.getTitle());
                    intent.putExtra("reviewText", currentReview.getReviewText());
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    public void bind(Review review) {
        currentReview = review;
        reviewTitleView.setText(review.getTitle());
        reviewTextView.setText(review.getReviewText());
    }
}
