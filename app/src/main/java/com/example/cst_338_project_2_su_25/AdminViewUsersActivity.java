package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AdminViewUsersActivity extends AppCompatActivity {
    private ListView userListView;
    private UserDao userDao;
    private List<User> userList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        userListView = findViewById(R.id.userListView);
        userDao = RevuDatabase.getDatabase(getApplicationContext()).userDao();

        Button btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(view -> {
            Intent intent = new Intent(AdminViewUsersActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Executors.newSingleThreadExecutor().execute(()-> {
            userList = userDao.getAllUsers();
            List<String> usernames = new ArrayList<>();

            for (User u : userList) {
                usernames.add(u.username + (u.isAdmin ? " (Admin)" : ""));
            }
            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
                userListView.setAdapter(adapter);
            });
        });
        userListView.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = userList.get(position);
            Executors.newSingleThreadExecutor().execute(()-> {
                userDao.deleteUser(selectedUser);
                userList.remove(position);
                runOnUiThread(() -> {
                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                });
            });
        });
    }
}
