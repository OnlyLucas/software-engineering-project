package com.example.software_engineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingScreen extends AppCompatActivity {


    private Button goBackButton;
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
    }
}