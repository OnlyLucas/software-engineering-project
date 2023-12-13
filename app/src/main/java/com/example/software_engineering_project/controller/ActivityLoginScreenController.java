package com.example.software_engineering_project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;

public class ActivityLoginScreenController extends AppCompatActivity {

    private Button loginButton, registerButtonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loadScreenElements();
        addButtons();

    }

    private void loadScreenElements() {

        loginButton = findViewById(R.id.loginButton);
        registerButtonLogin = findViewById(R.id.registerButtonLogin);

    }

    private void addButtons() {

        loginButton.setOnClickListener(view -> {
            Intent mainScreen = new Intent(ActivityLoginScreenController.this, ActivityMainScreenController.class);
            startActivity(mainScreen);
        });

        registerButtonLogin.setOnClickListener(view -> {
            Intent registerScreen = new Intent(ActivityLoginScreenController.this, ActivityRegisterScreenController.class);
            startActivity(registerScreen);
        });

    }

}