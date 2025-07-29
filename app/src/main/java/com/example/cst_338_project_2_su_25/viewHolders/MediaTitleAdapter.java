package com.example.cst_338_project_2_su_25.viewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.cst_338_project_2_su_25.R;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.Collections;
import java.util.List;

public class MediaTitleAdapter extends ListAdapter<MediaTitle, MediaTitleViewHolder> {

    //tracking fav id's
    private List<Integer> favoriteIds = Collections.emptyList();
    public MediaTitleAdapter(@NonNull DiffUtil.ItemCallback<MediaTitle> diffCallBack) {
        super(diffCallBack);
    }

    //gets used when user's fav id changes
    public void setFavoriteIds(List<Integer> ids) {
        this.favoriteIds = ids != null ? ids : Collections.emptyList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MediaTitleViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaTitleViewHolder holder, int position) {
        MediaTitle current = getItem(position);
        holder.bind(current.toString());

        Button heart = holder.itemView.findViewById(R.id.favoriteButton);

        // Shows the heart button only if this ID is in favoriteIds
        if (favoriteIds.contains(current.getMediaTitleId())) {
            heart.setVisibility(View.VISIBLE);
        } else {
            heart.setVisibility(View.GONE);
        }
    }

    public static class MediaTitleDiff extends DiffUtil.ItemCallback<MediaTitle> {
        @Override
        public boolean areContentsTheSame(@NonNull MediaTitle oldItem, @NonNull MediaTitle newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull MediaTitle oldItem, @NonNull MediaTitle newItem) {
            return oldItem == newItem;
        }
    }
}
