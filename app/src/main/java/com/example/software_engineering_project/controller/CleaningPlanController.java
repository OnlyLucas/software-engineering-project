package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.example.software_engineering_project.CleaningPlanEditFragment;
import com.example.software_engineering_project.CleaningPlanFragment;
import com.example.software_engineering_project.R;

import java.lang.reflect.Array;

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

        editCleaningPlan = findViewById(R.id.editCleaningPlan);
        editCleaningPlan.setOnClickListener(view -> {
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_cleaning_plan_edit, new CleaningPlanEditFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cleaning_plan, new CleaningPlanEditFragment()).commit();
        });






    }


}


