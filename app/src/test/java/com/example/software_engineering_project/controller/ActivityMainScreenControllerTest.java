package com.example.software_engineering_project.controller;

import android.os.Looper;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import com.example.software_engineering_project.R;

@RunWith(RobolectricTestRunner.class)
public class ActivityMainScreenControllerTest {

    private ActivityMainScreenController activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(ActivityMainScreenController.class).create().get();
    }

    @Test
    public void testButtonsNotNull() {
        assertNotNull(activity);

        assertNotNull("Budget button is null", activity.findViewById(R.id.budgetButton));
        assertNotNull("Cleaning Plan button is null", activity.findViewById(R.id.cleaningPlanButton));
        assertNotNull("Settings button is null", activity.findViewById(R.id.goSettingsButtons));
        assertNotNull("Grocery List button is null", activity.findViewById(R.id.groceryListButton));
    }

    //Example for Grocery List
    @Test
    public void testButtonClickOpensFragment() {
        // Click on the Grocery List button
        Button groceryListButton = activity.findViewById(R.id.groceryListButton);
        groceryListButton.performClick();

        // Allow the main looper to process any pending tasks
        shadowOf(Looper.getMainLooper()).idle();

        // Verify that the expected fragment is added to the container
        assertTrue(activity.getSupportFragmentManager().findFragmentById(R.id.contentFragmentMainScreen) instanceof FragmentGroceryListController);
    }

    // Example for Budget
    @Test
    public void testMenuBarTint() {
        // Click on the Budget button
        Button budgetButton = activity.findViewById(R.id.budgetButton);
        budgetButton.performClick();

        // Allow the main looper to process any pending tasks
        shadowOf(Looper.getMainLooper()).idle();

        // Verify that the background color of the clicked button is set to the primary color
        int expectedColor = activity.getResources().getColor(R.color.primary);
        assertTrue("Budget button background color is not set to primary color", budgetButton.getBackgroundTintList().getDefaultColor() == expectedColor);

        // Verify that the background color of other buttons is set to the background color
        int expectedBackgroundColor = activity.getResources().getColor(R.color.background);
        assertTrue("Cleaning Plan button background color is not set to background color", activity.findViewById(R.id.cleaningPlanButton).getBackgroundTintList().getDefaultColor() == expectedBackgroundColor);
        assertTrue("Settings button background color is not set to background color", activity.findViewById(R.id.goSettingsButtons).getBackgroundTintList().getDefaultColor() == expectedBackgroundColor);
        assertTrue("Grocery List button background color is not set to background color", activity.findViewById(R.id.groceryListButton).getBackgroundTintList().getDefaultColor() == expectedBackgroundColor);
    }

}

