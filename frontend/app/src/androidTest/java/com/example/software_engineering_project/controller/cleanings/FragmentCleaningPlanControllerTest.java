package com.example.software_engineering_project.controller.cleanings;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;
import com.example.software_engineering_project.controller.budget.FragmentBudgetMainController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentCleaningPlanControllerTest {

    /**
     * Prepares the test environment by logging into the application and initializing a specific fragment.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentCleaningPlanController} within {@link ActivityMainScreenController}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the specified fragment within
     * the {@link ActivityMainScreenController} is accessible after a successful login.
     * The method assumes the existence of the specified fragment and a valid navigation flow from
     * the login screen to the {@link ActivityMainScreenController}.
     * <p>
     * This setup method prepares the testing environment by performing necessary actions such as login
     * and launching a fragment for subsequent test execution.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentCleaningPlanController fragmentCleaningPlanController = new FragmentCleaningPlanController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentCleaningPlanController, R.id.contentFragmentMainScreen);

    }

    /**
     * Tests the visibility of UI elements within the Cleaning Plan fragment.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Checks if the "Add Cleaning Plan" button is displayed.</li>
     *     <li>Verifies that the "Go Back" and "Save Cleaning Plan" buttons are not visible initially.</li>
     * </ol>
     * <p>
     * Preconditions: The fragment containing the specified UI elements must be accessible and displayed.
     * The method assumes the existence of the specified UI elements within the fragment's layout.
     * <p>
     * This test method aims to ensure the correct initial visibility of UI elements
     * within the Cleaning Plan fragment.
     */
    @Test
    public void testFragmentUIElementsDisplayed() {

        Espresso.onView(withId(R.id.addCleaningPlan)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.goBackCleaningPlan)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }

    /**
     * Tests the behavior of the "Add Cleaning Plan" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Performs a click action on the "Add Cleaning Plan" button.</li>
     *     <li>Asserts the expected UI changes after clicking the button.</li>
     * </ol>
     * <p>
     * Assumptions: This test method allows testing scenarios related to the reaction of clicking the "Add Cleaning Plan" button.
     * <p>
     * Assertions for expected UI changes:
     * <ul>
     *     <li>Checks if the "Go Back" and "Save Cleaning Plan" buttons become visible.</li>
     *     <li>Verifies if the "Add Cleaning Plan" button becomes invisible after the click.</li>
     *     <li>Confirms the appearance of the expected fragment with the tag "fragment_cleaning_plan_add".</li>
     * </ul>
     */
    @Test
    public void testAddCleaningPlanButton() {

        // Annahme: Hier können Sie Szenarien testen, wie das Klicken auf den "Add Cleaning Plan" Button reagiert.
        Espresso.onView(withId(R.id.addCleaningPlan)).perform(ViewActions.click());

        // Hier können Sie Assertions für erwartete UI-Änderungen nach dem Klick auf den Button hinzufügen.
        Espresso.onView(withId(R.id.goBackCleaningPlan)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.addCleaningPlan)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentCleaningPlan)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_cleaning_plan_add")))));

    }

}