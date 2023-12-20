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

    /**
     * Initializes the main activity upon creation.
     * - Calls the superclass method for initialization.
     * - Sets the content view to the main screen layout.
     * - Loads and initializes UI elements required for the main screen.
     * - Assigns click listeners to buttons for navigating to different fragments.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        loadScreenElements();
        addButtons();

    }

    /**
     * Assigns click listeners to various buttons on the main screen to handle navigation to different fragments.
     * - Sets an onClickListener for the grocery list button to navigate to the grocery list fragment.
     * - Sets an onClickListener for the settings button to navigate to the settings fragment.
     * - Sets an onClickListener for the cleaning plan button to navigate to the cleaning plan fragment.
     * - Sets an onClickListener for the budget button to navigate to the budget fragment.
     * Additionally, it applies a tint to the clicked button to indicate the active state.
     */
    private void addButtons() {

        groceryListButton.setOnClickListener(view -> {
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

    /**
     * Applies a tint to the menu bar buttons to indicate the active button.
     * - Changes the background color of all menu bar buttons to a default background color.
     * - Changes the background color of the provided button to a specific primary color to indicate its active state.
     *
     * @param button The Button object representing the menu bar button that needs to be highlighted.
     */
    private void addMenuBarTint(Button button) {

        cleaningPlanButton.setBackgroundColor(getColor(R.color.background));
        goSettingsButton.setBackgroundColor(getColor(R.color.background));
        groceryListButton.setBackgroundColor(getColor(R.color.background));
        budgetButton.setBackgroundColor(getColor(R.color.background));
        button.setBackgroundColor(getColor(R.color.primary));

    }

    /**
     * Replaces the current fragment in the main screen content container with a new fragment.
     * - Uses the FragmentManager to begin a FragmentTransaction for replacing the current fragment.
     * - Replaces the fragment within the R.id.contentFragmentMainScreen container with the new fragment.
     * - Associates a tag with the new fragment for later identification if required.
     *
     * @param fragment The Fragment object to be displayed within the main screen content container.
     * @param tag      A String tag associated with the fragment for identification purposes.
     */
    public void callFragment(Fragment fragment, String tag) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment, tag);
        transaction.commit();

    }

    /**
     * Loads and initializes UI elements from the main screen layout.
     * - Retrieves and assigns buttons for managing budget, cleaning plans, settings, and grocery lists.
     *   These buttons are essential for navigation and managing different functionalities within the main screen.
     */
    private void loadScreenElements() {

        budgetButton = findViewById(R.id.budgetButton);
        cleaningPlanButton = findViewById(R.id.cleaningPlanButton);
        goSettingsButton = findViewById(R.id.goSettingsButtons);
        groceryListButton = findViewById(R.id.groceryListButton);

    }

}