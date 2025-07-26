package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button signupButton, backToLoginButton;
    private RevuDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupButton = findViewById(R.id.signupButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);

        db = RevuDatabase.getDatabase(getApplicationContext());
        userDao = db.userDao();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim().toLowerCase();
                String password = passwordInput.getText().toString().trim().toLowerCase();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    User newUser = new User();
                    newUser.username = username;
                    newUser.password = password;
                    newUser.isAdmin = false;

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        User existingUser = userDao.getUserByUsername(username.toLowerCase());
                        if(existingUser == null) {
                            userDao.insertUser(newUser);
                            runOnUiThread(() -> {
                                Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(SignUpActivity.this, "Username already exists. Please choose another.", Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                }
            }
        });

        backToLoginButton.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
