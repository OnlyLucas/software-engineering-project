package com.example.software_engineering_project.controller;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.appsettings.FragmentSettingsController;
import com.example.software_engineering_project.controller.budget.FragmentBudgetMainController;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanController;
import com.example.software_engineering_project.controller.groceries.FragmentGroceryListController;


/**
 * ActivityMainScreenController is the main screen controller for the FlatFusion app.
 * It provides buttons for navigating to different features, such as budget, cleaning plan,
 * grocery list, and settings.
 */
public class ActivityMainScreenController extends AppCompatActivity {

    private Button budgetButton, cleaningPlanButton, goSettingsButton, groceryListButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        loadScreenElements();
        addButtons();

    }

    /**
     * Adds click listeners to the buttons for navigating to different features.
     */
    private void addButtons() {

        groceryListButton.setOnClickListener(view -> {
            // TODO
            // FragmentGroceryListController.groceryLiveData.getValue().clear();
            FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
            String tag = "fragment_grocery_list";
            callFragment(fragmentGroceryListController, tag);
            addMenuBarTint(groceryListButton);
        });

        goSettingsButton.setOnClickListener(view -> {
            FragmentSettingsController fragmentSettingsController = new FragmentSettingsController();
            String tag = "fragment_setting_screen";
            callFragment(fragmentSettingsController, tag);
            addMenuBarTint(goSettingsButton);

        });

        cleaningPlanButton.setOnClickListener(view -> {
            FragmentCleaningPlanController fragmentCleaningPlanController = new FragmentCleaningPlanController();
            String tag = "fragment_cleaning_plan";
            callFragment(fragmentCleaningPlanController, tag);
            addMenuBarTint(cleaningPlanButton);
        });

        budgetButton.setOnClickListener(view -> {
            FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
            String tag = "fragment_budget_main";
            callFragment(fragmentBudgetMainController, tag);
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

    /**
     * Calls the specified fragment with the given tag for fragment management.
     *
     * @param fragment The fragment to be displayed.
     * @param tag      The tag for the fragment.
     */
    public void callFragment(Fragment fragment, String tag) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment,tag);
        transaction.commit();

    }

    private void loadScreenElements() {

        budgetButton = findViewById(R.id.budgetButton);
        cleaningPlanButton = findViewById(R.id.cleaningPlanButton);
        goSettingsButton = findViewById(R.id.goSettingsButtons);
        groceryListButton = findViewById(R.id.groceryListButton);

    }

}