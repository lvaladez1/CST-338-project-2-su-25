package com.example.cst_338_project_2_su_25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.MediaTitleDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaTitleHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MediaTitleDAO mediaTitleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_history);

        recyclerView = findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mediaTitleDAO = RevuDatabase.getDatabase(getApplicationContext()).mediaTitleDAO();
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<MediaTitle> mediaList = mediaTitleDAO.getAllMediaTitles(); // or filter by userId if needed
            runOnUiThread(() -> {
                recyclerView.setAdapter(new MediaTitleAdapter(mediaList)); // uses updated adapter
            });
        });
    }
}
