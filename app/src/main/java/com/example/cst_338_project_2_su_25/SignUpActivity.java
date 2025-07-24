package com.example.cst_338_project_2_su_25;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button signupButton, backToLoginButton;
    private RevuDatabase db;
    private UserDao userDao;
}
