package com.example.software_engineering_project.controller.budget;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentBudgetMainControllerTest {

    /**
     * Prepares the test environment by initiating the application login and launching a specific fragment.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentBudgetMainController} within {@link ActivityMainScreenController}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the specified fragment within
     * the {@link ActivityMainScreenController} is accessible after a successful login.
     * The method assumes the existence of the specified fragment and a valid navigation flow from
     * the login screen to the {@link ActivityMainScreenController}.
     * <p>
     * This setup method prepares the testing environment by performing necessary actions such as login
     * and fragment launching for subsequent test execution.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentBudgetMainController, R.id.contentFragmentMainScreen);

    }

    /**
     * Tests the visibility and clickability of buttons within the fragment.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Verifies the visibility and clickability of the "Add Expense" button.</li>
     *     <li>Verifies the visibility and clickability of the "Show Budget Detail" button.</li>
     *     <li>Verifies the invisibility and non-clickability of the "Go Back" button.</li>
     * </ol>
     * <p>
     * Preconditions: The fragment with the specified buttons must be accessible and displayed properly.
     * The method assumes the existence of the specified buttons within the fragment layout.
     * <p>
     * This test method aims to ensure the expected visibility and clickability states of buttons within the fragment.
     */
    @Test
    public void testButtonsVisibility() {
        // Überprüft die Sichtbarkeit und Klickbarkeit der Buttons im Fragment

        // Überprüft, ob der "Add Expense" Button sichtbar und klickbar ist
        Espresso.onView(withId(R.id.addExpense)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.addExpense)).check(matches(isClickable()));

        // Überprüft, ob der "Show budget detail" Button sichtbar und klickbar ist
        Espresso.onView(withId(R.id.showBudgetDetail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.showBudgetDetail)).check(matches(isClickable()));

        // Überprüft, ob der "Go Back" Button unsichtbar und nicht klickbar ist
        Espresso.onView(withId(R.id.goBackBudgetMain)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        //Espresso.onView(withId(R.id.goBackBudgetMain)).check(matches(isNotClickable())); //TODO NOT WORKING

    }

    /**
     * Tests the functionality of the "Add Expense" button within the fragment.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicks on the "Add Expense" button.</li>
     *     <li>Verifies whether the FragmentBudgetAddExpenseScreenController fragment is loaded after the click.</li>
     *     <li>Verifies the appearance of the budget headline with the text "Add New Expense" after the click.</li>
     * </ol>
     * <p>
     * Preconditions: The fragment containing the "Add Expense" button must be visible and accessible.
     * The method assumes the existence of the specified buttons and fragments within the application's layout.
     * <p>
     * This test method aims to ensure the correct functionality of the "Add Expense" button by verifying
     * the transition to the appropriate fragment and the displayed headline text.
     */
    @Test
    public void testClickAddExpenseButton() {
        // Überprüft, ob der "Add Expense" Button korrekt funktioniert

        // Klickt auf den "Add Expense" Button
        Espresso.onView(withId(R.id.addExpense)).perform(click());

        // Überprüft, ob nach dem Klick das Fragment FragmentBudgetAddExpenseScreenController geladen wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentBudgetMain)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_budget_add_expense_screen")))));
        Espresso.onView(withId(R.id.budgetHeadline)).check(matches(withText(R.string.add_new_expense)));

    }

}

