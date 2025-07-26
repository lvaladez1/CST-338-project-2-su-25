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
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
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
