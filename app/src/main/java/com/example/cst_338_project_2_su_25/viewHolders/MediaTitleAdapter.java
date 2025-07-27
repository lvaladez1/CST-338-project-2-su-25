package com.example.cst_338_project_2_su_25.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.cst_338_project_2_su_25.entities.MediaTitle;

public class MediaTitleAdapter extends ListAdapter<MediaTitle, MediaTitleViewHolder> {

    public MediaTitleAdapter(@NonNull DiffUtil.ItemCallback<MediaTitle> diffCallBack) {
        super(diffCallBack);
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
