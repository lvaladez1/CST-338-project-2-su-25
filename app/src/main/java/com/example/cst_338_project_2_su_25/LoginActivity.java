package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, signupButton;
    private RevuDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        db = RevuDatabase.getDatabase(this);
        userDao = db.userDao();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                User user = userDao.userLogin(username, password);

                if(user != null) {
                    SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
                    prefs.edit().putInt("userId", user.userId).apply();
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






}
