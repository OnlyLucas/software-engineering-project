package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.example.software_engineering_project.R;

public class CleaningPlanController extends AppCompatActivity {

    private Button goBackButtonCleaningPlan;
   private  Button editCleaningPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_plan);
        this.addButtons();
    }


    private void addButtons() {
        goBackButtonCleaningPlan = findViewById(R.id.goBackButtonCleaningPlan);
        goBackButtonCleaningPlan.setOnClickListener(view -> {
            Intent MainScreen = new Intent(CleaningPlanController.this, MainScreenController.class);
            startActivity(MainScreen);
        });
    }
}


