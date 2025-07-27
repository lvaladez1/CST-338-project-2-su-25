package com.example.cst_338_project_2_su_25.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.R;

public class MediaTitleViewHolder extends RecyclerView.ViewHolder {
    private final TextView mediaTitleViewItem;

    private MediaTitleViewHolder(View mediaTitleView) {
        super(mediaTitleView);
        mediaTitleViewItem = mediaTitleView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text) {
        mediaTitleViewItem.setText(text);
    }

    static MediaTitleViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_title_recycler_item, parent, false);
        return new MediaTitleViewHolder(view);
    }
}
