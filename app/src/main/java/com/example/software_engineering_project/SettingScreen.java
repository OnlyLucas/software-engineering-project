package com.example.software_engineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingScreen extends AppCompatActivity {


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
            Intent MainScreen = new Intent(SettingScreen.this,MainScreen.class);
            startActivity(MainScreen);
        });

        changePasswordScreenButton = findViewById(R.id.changePasswordScreenButton);
        changePasswordScreenButton.setOnClickListener(view -> {
            Intent ChangePasswordScreen = new Intent(SettingScreen.this,ChangePasswordScreen.class);
            startActivity(ChangePasswordScreen);
        });

        changeMailScreenButton = findViewById(R.id.changeMailScreenButton);
        changeMailScreenButton.setOnClickListener(view -> {
            Intent ChangeMailScreen = new Intent(SettingScreen.this, ChangeMailScreen.class);
            startActivity(ChangeMailScreen);
        });

        manageFlatShareButton = findViewById(R.id.manageFlatShareButton);
        manageFlatShareButton.setOnClickListener(view -> {
            Intent ManageFlatShareScreen = new Intent(SettingScreen.this, ManageFlatShareMain.class);
            startActivity(ManageFlatShareScreen);
        });
    }
}