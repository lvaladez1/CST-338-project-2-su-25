package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.databinding.ActivityDisplayMediaBinding;
import com.example.cst_338_project_2_su_25.viewHolders.MediaTitleAdapter;


public class DisplayMediaActivity extends AppCompatActivity {
    ActivityDisplayMediaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.displayMediaTitleRecyclerView;
        final MediaTitleAdapter adapter = new MediaTitleAdapter(new MediaTitleAdapter.MediaTitleDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String mediaTitle = getIntent().getStringExtra("mediaTitle");
        binding.mediaTitleTextView.setText(mediaTitle);

        binding.addMediaTitleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddMediaActivity.addMediaIntentFactory(getApplicationContext());
                intent.putExtra("addMedia", "Add TV Show");
                startActivity(intent);
            }
        });


    }

    static Intent displayMediaIntentFactory(Context context) {
        return new Intent(context, DisplayMediaActivity.class);
    }
}