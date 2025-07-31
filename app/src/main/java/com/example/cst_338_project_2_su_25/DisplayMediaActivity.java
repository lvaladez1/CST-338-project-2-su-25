package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityDisplayMediaBinding;
import com.example.cst_338_project_2_su_25.viewHolders.MediaTitleAdapter;
import com.example.cst_338_project_2_su_25.viewHolders.MediaTitleViewModel;


public class DisplayMediaActivity extends AppCompatActivity {
    private ActivityDisplayMediaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String mediaTitle = getIntent().getStringExtra("mediaTitle");
        binding.mediaTitleTextView.setText(mediaTitle);

        MediaTitleViewModel mediaTitleViewModel = new ViewModelProvider(this).get(MediaTitleViewModel.class);

        RecyclerView recyclerView = binding.displayMediaTitleRecyclerView;
        final MediaTitleAdapter adapter = new MediaTitleAdapter(new MediaTitleAdapter.MediaTitleDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        int loggedInUserId = prefs.getInt("userId", -1);

        RevuRepository.getRepository(getApplication());

        String MEDIA_TYPE_MOVIE = "Movies";
        String MEDIA_TYPE_TV_SHOW = "TV Shows";
        if (binding.mediaTitleTextView.getText().toString().equals(MEDIA_TYPE_TV_SHOW)) {
            mediaTitleViewModel
                    .getAllLiveDataTvShowsByUserId(loggedInUserId)
                    .observe(this, adapter::submitList);

        } else if (binding.mediaTitleTextView.getText().toString().equals(MEDIA_TYPE_MOVIE)) {
            mediaTitleViewModel
                    .getAllLiveDataMoviesByUserId(loggedInUserId)
                    .observe(this, adapter::submitList);
        }

        RevuDatabase.getDatabase(getApplicationContext())
                .favoritesDAO()
                .getFavoriteMediaIdsForUser(loggedInUserId)
                .observe(this, ids -> {
                    adapter.setFavoriteIds(ids);


                    binding.addMediaTitleFAB.setOnClickListener(v -> {
                        if (binding.mediaTitleTextView.getText().toString().equals("TV Shows")) {
                            Intent intent = AddMediaActivity.addMediaIntentFactory(getApplicationContext());
                            intent.putExtra("addMedia", "Add TV Show");
                            startActivity(intent);
                        } else if (binding.mediaTitleTextView.getText().toString().equals("Movies")) {
                            Intent intent = AddMediaActivity.addMediaIntentFactory(getApplicationContext());
                            intent.putExtra("addMedia", "Add Movie");
                            startActivity(intent);
                        }
                    });

                    binding.backToLoginFAB.setOnClickListener(v -> {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    });

                });
    }

        static Intent displayMediaIntentFactory (Context context){
            return new Intent(context, DisplayMediaActivity.class);
        }
    }
