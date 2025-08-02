package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminCreateUserActivity extends AppCompatActivity {
    private EditText newUsernameInput, newPasswordInput;
    private CheckBox newAdminCheckbox;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);

        newUsernameInput = findViewById(R.id.newUsernameInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        Button createUserButton = findViewById(R.id.createUserButton);
        newAdminCheckbox = findViewById(R.id.newAdminCheckbox);
        RevuDatabase db = RevuDatabase.getDatabase(this);
        userDao = db.userDao();

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCreateUserActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        createUserButton.setOnClickListener(view -> {
            String username = newUsernameInput.getText().toString().trim().toLowerCase();
            String password = newPasswordInput.getText().toString().trim();
            boolean isAdmin = newAdminCheckbox.isChecked();
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->{
                if(userDao.getUserByUsername(username) != null) {
                    runOnUiThread(() -> Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show());
                }
                else {

                    User newUser = new User();
                    newUser.username = username;
                    newUser.password = password;
                    newUser.isAdmin = isAdmin;
                    userDao.insertUser(newUser);
                    runOnUiThread(()-> {
                        Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }
    static Intent adminCreateUserIntentFactory(Context context) {
        return new Intent(context, AdminCreateUserActivity.class);
    }
}
