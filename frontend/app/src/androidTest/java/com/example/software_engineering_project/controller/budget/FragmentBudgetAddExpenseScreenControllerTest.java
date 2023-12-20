package com.example.software_engineering_project.controller.budget;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentBudgetAddExpenseScreenControllerTest {

    /**
     * Rule to facilitate testing of {@link ActivityMainScreenController} using Espresso Intents.
     * <p>
     * This rule is used to launch and handle the lifecycle of the {@link ActivityMainScreenController}
     * for testing purposes using {@link IntentsTestRule} provided by Espresso.
     * <p>
     * Preconditions: The {@link ActivityMainScreenController} must be a valid activity intended for testing.
     * <p>
     * This rule enables the setup and teardown of the activity lifecycle, allowing the execution of Espresso
     * test cases within the context of the {@link ActivityMainScreenController} to test its UI interactions
     * and Intent interactions with other activities.
     */
    @Rule
    public IntentsTestRule<ActivityMainScreenController> activityRule = new IntentsTestRule<>(ActivityMainScreenController.class);

    /**
     * Initializes the test environment by launching a specific fragment and performing an initial action.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentBudgetMainController} within {@link ActivityMainScreenController}.</li>
     *     <li>Performing a click action on the "Add Expense" button with the ID {@code R.id.addExpense}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the user has access to the specified fragment.
     * The method assumes the existence of the specified UI elements and the availability of the corresponding fragment.
     * <p>
     * This setup method initializes the testing environment by launching a specific fragment and performing
     * an initial action, facilitating subsequent test executions.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentBudgetMainController, R.id.contentFragmentMainScreen);

        Espresso.onView(withId(R.id.addExpense)).perform(ViewActions.click());

    }

    /**
     * Validates the functionality of adding an expense.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Entering data into the "Expense Amount" field with the ID {@code R.id.enterNewExpenseAmount}.</li>
     *     <li>Entering data into the "Expense Reason" field with the ID {@code R.id.enterNewExpenseReason}.</li>
     *     <li>Clicking on the involved persons list item with the ID {@code R.id.enterNewExpenseInvolvedPersons}.</li>
     *     <li>Waiting for a specified time using {@link TestUtils#waitingTime()} to ensure visibility of the "Save Expense" button.</li>
     *     <li>Clicking on the "Save Expense" button with the ID {@code R.id.saveExpense} and checking if it's visible.</li>
     *     <li>Verifying whether the {@link ActivityMainScreenController} is launched after the expense is saved.</li>
     *     <li>Checking if the {@link ActivityLoginScreenController} is launched.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where adding expenses is possible.
     * The method assumes the existence of the specified UI elements and the availability of the expense adding functionality.
     * <p>
     * This test method aims to confirm that adding an expense correctly performs the required actions and navigates
     * to the expected screens upon saving the expense.
     */
    @Test
    public void testAddExpense() {

        // Enter some data into the expense and reason fields
        Espresso.onView(withId(R.id.enterNewExpenseAmount)).perform(ViewActions.typeText("50"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterNewExpenseReason)).perform(ViewActions.typeText("Test expense"), ViewActions.closeSoftKeyboard());

        // Click on the involved persons list item
        Espresso.onView(withId(R.id.enterNewExpenseInvolvedPersons)).perform(click());


        // Wait for some time to ensure the saveExpense button is visible
        TestUtils.waitingTime();

        // Click on the save button and check if the save button is visible
        Espresso.onView(ViewMatchers.withId(R.id.saveExpense))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Überprüfe, ob die ActivityLoginScreenController gestartet wird
        Intents.intending(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));

        // Test if ActivityLoginScreenController is launched
        Intents.intending(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
    }

}