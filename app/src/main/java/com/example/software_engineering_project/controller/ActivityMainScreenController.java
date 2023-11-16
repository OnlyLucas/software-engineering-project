package com.example.software_engineering_project.controller;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;

public class ActivityMainScreenController extends AppCompatActivity {

    private Button goSettingsButton;
    private Button groceryListButton;
    private Button cleaningPlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        addButtons();

    }

    public void callFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }


    private void addButtons() {

        groceryListButton = findViewById(R.id.groceryListButton);
        groceryListButton.setOnClickListener(view -> {
            FragmentGroceryListController.items.clear();
            FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
            callFragment(fragmentGroceryListController);
            addMenuBarTint(groceryListButton);
        });

        goSettingsButton = findViewById(R.id.goSettingsButtons);
        goSettingsButton.setOnClickListener(view -> {
            FragmentSettingsController fragmentSettingsController = new FragmentSettingsController();
            callFragment(fragmentSettingsController);
            addMenuBarTint(goSettingsButton);

        });

        cleaningPlanButton = findViewById(R.id.cleaningPlanButton);
        cleaningPlanButton.setOnClickListener(view -> {
            FragmentCleaningPlanController fragmentCleaningPlanController = new FragmentCleaningPlanController();
            callFragment(fragmentCleaningPlanController);
            addMenuBarTint(cleaningPlanButton);
        });

    }

    private void addMenuBarTint(Button button) {

        cleaningPlanButton.setBackgroundColor(getColor(R.color.background));
        goSettingsButton.setBackgroundColor(getColor(R.color.background));
        groceryListButton.setBackgroundColor(getColor(R.color.background));
        button.setBackgroundColor(getColor(R.color.navBarSelected));

    }

}