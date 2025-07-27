package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityAddMediaBinding;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;

public class AddMediaActivity extends AppCompatActivity {

    private RevuRepository repository;
    String title = "";
    String type = "";
    String genre = "";
    String review = "";
    int loggedInUserId = -1;
    ActivityAddMediaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String addMedia = getIntent().getStringExtra("addMedia");
        binding.addMediaTextView.setText(addMedia);

        repository = RevuRepository.getRepository(getApplication());

        binding.btnCancelAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                intent.putExtra("mediaTitle", "TV Shows");
                startActivity(intent);
            }
        });

        binding.btnSaveMediaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre = "tvShow";
                insertMediaRecord();
                Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                intent.putExtra("mediaTitle", "TV Shows");
                startActivity(intent);
            }
        });
    }

    static Intent addMediaIntentFactory(Context context){
        return new Intent(context, AddMediaActivity.class);
    }

    private void insertMediaRecord(){
        if (title.isEmpty()){
            return;
        }
        MediaTitle mediaTitle = new MediaTitle(title, type, genre, loggedInUserId);
        repository.insertMediaTitle(mediaTitle);
    }
}