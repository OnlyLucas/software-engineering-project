package com.example.software_engineering_project.controller;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.groceries.FragmentGroceryListController;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
public class ActivityMainScreenControllerTest {

    /**
     * Espresso test rule that launches the ActivityMainScreenController before each test annotated with @Test.
     * <p>
     * This rule initializes and launches the ActivityMainScreenController to provide a test environment for Espresso tests.
     * It ensures that the activity is properly set up and available for testing purposes.
     * <p>
     * Usage:
     * Define this rule at the beginning of Espresso test classes to automatically launch ActivityMainScreenController
     * before executing any test annotated with @Test.
     * <p>
     * Example:
     * {@code @Rule
     * public IntentsTestRule<ActivityMainScreenController> mActivityRule = new IntentsTestRule<>(ActivityMainScreenController.class);}
     * <p>
     * This rule simplifies the setup process by automatically launching the activity and making it available for testing.
     */
    @Rule
    public IntentsTestRule<ActivityMainScreenController> mActivityRule = new IntentsTestRule<>(ActivityMainScreenController.class);

    /**
     * Sets up the initial state before each test execution.
     * <p>
     * Launches the app and logs in to the application using TestUtils.appLogin() method.
     * <p>
     * This method is annotated with @Before, signifying that it will run before each test method in the test class.
     * It ensures the application is in a logged-in state, providing a consistent starting point for tests.
     * <p>
     * Example usage:
     * {@code @Before
     * public void setUp() {
     *     // Launch the activity and perform initial actions
     *     TestUtils.appLogin();
     * }}
     * <p>
     * This method prepares the environment for test cases, such as establishing a logged-in session for subsequent tests.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

    }

    /**
     * Tests the functionality of the "Grocery List" button.
     * <p>
     * Performs a click action on the "Grocery List" button.
     * <p>
     * Verifies whether the associated fragment is loaded in the FragmentContainerView by checking for the presence
     * of the expected fragment with a specific tag value within the main screen content view.
     * <p>
     * This method is annotated with @Test, signifying that it's a test method within the test class and will be executed
     * by the testing framework.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testGroceryListButton() {
     *     // Perform actions to click the "Grocery List" button and verify fragment loading
     *     // Assertions to verify the presence of the expected fragment are performed here
     * }}
     * <p>
     * This method tests the behavior of the "Grocery List" button, ensuring that the associated fragment is loaded
     * correctly in the designated container upon button click.
     */
    @Test
    public void testGroceryListButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.groceryListButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_grocery_list")))));

    }

    /**
     * Tests the functionality of the "Settings" button.
     * <p>
     * Performs a click action on the "Settings" button.
     * <p>
     * Verifies whether the associated fragment is loaded in the FragmentContainerView by checking for the presence
     * of the expected fragment with a specific tag value within the main screen content view.
     * <p>
     * This method is annotated with @Test, signifying that it's a test method within the test class and will be executed
     * by the testing framework.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testSettingsButton() {
     *     // Perform actions to click the "Settings" button and verify fragment loading
     *     // Assertions to verify the presence of the expected fragment are performed here
     * }}
     * <p>
     * This method tests the behavior of the "Settings" button, ensuring that the associated fragment is loaded
     * correctly in the designated container upon button click.
     */
    @Test
    public void testSettingsButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.goSettingsButtons)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_setting_screen")))));

    }

    /**
     * Tests the functionality of the "Cleaning Plan" button.
     * <p>
     * Performs a click action on the "Cleaning Plan" button.
     * <p>
     * Verifies whether the associated fragment is loaded in the FragmentContainerView by checking for the presence
     * of the expected fragment with a specific tag value within the main screen content view.
     * <p>
     * This method is annotated with @Test, signifying that it's a test method within the test class and will be executed
     * by the testing framework.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testCleaningPlanButton() {
     *     // Perform actions to click the "Cleaning Plan" button and verify fragment loading
     *     // Assertions to verify the presence of the expected fragment are performed here
     * }}
     * <p>
     * This method tests the behavior of the "Cleaning Plan" button, ensuring that the associated fragment is loaded
     * correctly in the designated container upon button click.
     */
    @Test
    public void testCleaningPlanButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.cleaningPlanButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_cleaning_plan")))));

    }

    /**
     * Tests the functionality of the "Budget" button.
     * <p>
     * Performs a click action on the "Budget" button.
     * <p>
     * Verifies whether the associated fragment is loaded in the FragmentContainerView by checking for the presence
     * of the expected fragment with a specific tag value within the main screen content view.
     * <p>
     * This method is annotated with @Test, signifying that it's a test method within the test class and will be executed
     * by the testing framework.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testBudgetButton() {
     *     // Perform actions to click the "Budget" button and verify fragment loading
     *     // Assertions to verify the presence of the expected fragment are performed here
     * }}
     * <p>
     * This method tests the behavior of the "Budget" button, ensuring that the associated fragment is loaded
     * correctly in the designated container upon button click.
     */
    @Test
    public void testBudgetButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.budgetButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_budget_main")))));

    }

}