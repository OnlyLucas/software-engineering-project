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
    private Button budgetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        loadScreenElements();
        addButtons();

    }

    private void loadScreenElements() {

        groceryListButton = findViewById(R.id.groceryListButton);
        goSettingsButton = findViewById(R.id.goSettingsButtons);
        cleaningPlanButton = findViewById(R.id.cleaningPlanButton);
        budgetButton = findViewById(R.id.budgetButton);
    }

    public void callFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }


    private void addButtons() {

        groceryListButton.setOnClickListener(view -> {
            FragmentGroceryListController.items.clear();
            FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
            callFragment(fragmentGroceryListController);
            addMenuBarTint(groceryListButton);
        });

        goSettingsButton.setOnClickListener(view -> {
            FragmentSettingsController fragmentSettingsController = new FragmentSettingsController();
            callFragment(fragmentSettingsController);
            addMenuBarTint(goSettingsButton);

        });

        cleaningPlanButton.setOnClickListener(view -> {
            FragmentCleaningPlanController fragmentCleaningPlanController = new FragmentCleaningPlanController();
            callFragment(fragmentCleaningPlanController);
            addMenuBarTint(cleaningPlanButton);
        });

        budgetButton.setOnClickListener(view -> {
            FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
            callFragment(fragmentBudgetMainController);
            addMenuBarTint(budgetButton);
        });

    }

    private void addMenuBarTint(Button button) {

        cleaningPlanButton.setBackgroundColor(getColor(R.color.background));
        goSettingsButton.setBackgroundColor(getColor(R.color.background));
        groceryListButton.setBackgroundColor(getColor(R.color.background));
        budgetButton.setBackgroundColor(getColor(R.color.background));
        button.setBackgroundColor(getColor(R.color.primary));

    }

}