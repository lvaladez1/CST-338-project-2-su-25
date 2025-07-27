package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityDisplayMediaBinding;
import com.example.cst_338_project_2_su_25.viewHolders.MediaTitleAdapter;
import com.example.cst_338_project_2_su_25.viewHolders.MediaTitleViewModel;


public class DisplayMediaActivity extends AppCompatActivity {
    private ActivityDisplayMediaBinding binding;
    private RevuRepository repository;
    private MediaTitleViewModel mediaTitleViewModel;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String mediaTitle = getIntent().getStringExtra("mediaTitle");
        binding.mediaTitleTextView.setText(mediaTitle);

        mediaTitleViewModel = new ViewModelProvider(this).get(MediaTitleViewModel.class);

        RecyclerView recyclerView = binding.displayMediaTitleRecyclerView;
        final MediaTitleAdapter adapter = new MediaTitleAdapter(new MediaTitleAdapter.MediaTitleDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("userId", loggedInUserId); // assuming getUserId() returns an int
        editor.apply();
        loggedInUserId = prefs.getInt("userId", -1);

        repository = RevuRepository.getRepository(getApplication());
        mediaTitleViewModel.getAllLiveDataTvShowsByUserId(loggedInUserId).observe(this, mediaTitles -> {
            adapter.submitList(mediaTitles);
        });

        binding.addMediaTitleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddMediaActivity.addMediaIntentFactory(getApplicationContext());
                intent.putExtra("addMedia", "Add TV Show");
                startActivity(intent);
            }
        });

        binding.backToLoginFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    static Intent displayMediaIntentFactory(Context context) {
        return new Intent(context, DisplayMediaActivity.class);
    }


}