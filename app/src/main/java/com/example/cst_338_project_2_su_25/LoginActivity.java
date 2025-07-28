package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, signupButton;
    private RevuDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        String loggedInUser = prefs.getString("loggedInUser", null);
        if(loggedInUser != null) {
            Log.d("LoginActivity", "User already logged in: " + loggedInUser);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", loggedInUser);
            startActivity(intent);
            finish();
            return;
        }


        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        db = RevuDatabase.getDatabase(this);
        userDao = db.userDao();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim().toLowerCase();
                String password = passwordInput.getText().toString().trim();
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    User user = userDao.userLogin(username, password);
                    runOnUiThread(() -> {
                        if (user != null) {
                            Log.d("LOGIN SUCCESS", "Found user: " + user.username + ", isAdmin: " + user.isAdmin);
                            SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("userId", user.getUserId());
                            editor.putBoolean("isAdmin", user.isAdmin);
                            editor.putString("loggedInUser", user.username);
                            editor.apply();



                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("LOGIN FAILED", "No user found with username: " + username + " and password: " + password);
                            Toast.makeText(LoginActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                        }
                    });
                });


                signupButton.setOnClickListener(v -> {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                });
            }
        });
    }
}