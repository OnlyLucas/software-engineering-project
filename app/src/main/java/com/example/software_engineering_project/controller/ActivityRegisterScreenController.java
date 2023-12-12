package com.example.software_engineering_project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;

public class ActivityRegisterScreenController extends AppCompatActivity {

    private Button cancelButton, registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        loadScreenElements();
        addButtons();

    }

    private void addButtons() {

        cancelButton.setOnClickListener(view -> {
            Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
            startActivity(loginScreen);
        });

        //Todo functionality for Register
        //registerButton.setOnClickListener();

    }

    private void loadScreenElements() {

        //Todo assign IDs to RegisterScreen's Elements and instantiate
        cancelButton = findViewById(R.id.cancelButton);
        registerButton = findViewById(R.id.registerButton);

    }

}