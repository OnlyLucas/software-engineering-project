package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.software_engineering_project.R;

public class SettingScreenController extends AppCompatActivity {


    private Button goBackButton;
    private Button changePasswordScreenButton;
    private Button changeMailScreenButton;

    private Button manageFlatShareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        this.addButtons();
    }

    private void addButtons(){
        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(view -> {
            Intent MainScreen = new Intent(SettingScreenController.this, MainScreenController.class);
            startActivity(MainScreen);
        });

        changePasswordScreenButton = findViewById(R.id.changePasswordScreenButton);
        changePasswordScreenButton.setOnClickListener(view -> {
            Intent ChangePasswordScreen = new Intent(SettingScreenController.this, ChangePasswordScreenController.class);
            startActivity(ChangePasswordScreen);
        });

        changeMailScreenButton = findViewById(R.id.changeMailScreenButton);
        changeMailScreenButton.setOnClickListener(view -> {
            Intent ChangeMailScreen = new Intent(SettingScreenController.this, ChangeMailScreenController.class);
            startActivity(ChangeMailScreen);
        });

        manageFlatShareButton = findViewById(R.id.manageFlatShareButton);
        manageFlatShareButton.setOnClickListener(view -> {
            Intent ManageFlatShareScreen = new Intent(SettingScreenController.this, ManageFlatShareController.class);
            startActivity(ManageFlatShareScreen);
        });

        manageFlatShareButton = findViewById(R.id.createFlatShareButton);
        manageFlatShareButton.setOnClickListener(view -> {
            Intent CreateFlatShareScreen = new Intent(SettingScreenController.this, CreateFlatShareScreenController.class);
            startActivity(CreateFlatShareScreen);
        });
    }
}