package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        setContentView(R.layout.fragment_setting_screen);
        this.addButtons();
    }

    public void callFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
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
            Fragment fragment = new Fragment(R.layout.fragment_manage_flat_share_screen);
            callFragment(fragment);

        });

        manageFlatShareButton = findViewById(R.id.createFlatShareButton);
        manageFlatShareButton.setOnClickListener(view -> {
            Intent CreateFlatShareScreen = new Intent(SettingScreenController.this, CreateFlatShareScreenController.class);
            startActivity(CreateFlatShareScreen);
        });
    }
}