package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.software_engineering_project.R;

public class ActivityRegisterScreenController extends AppCompatActivity {


    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        this.addButtons();

    }

    private void addButtons(){
        cancelButton=findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
            startActivity(loginScreen);
        });
    }


}