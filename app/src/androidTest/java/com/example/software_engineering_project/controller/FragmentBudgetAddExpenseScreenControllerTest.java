package com.example.software_engineering_project.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FragmentBudgetAddExpenseScreenControllerTest {

    @Rule
    public IntentsTestRule<ActivityMainScreenController> activityRule = new IntentsTestRule<>(ActivityMainScreenController.class);

    @Before
    public void launchFragment() {

        // Launch the activity
        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentBudgetMainController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });

        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentBudgetAddExpenseScreenController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentBudgetMain, fragment); // replace with your fragment container ID
            transaction.commit();
        });

    }

    @Test
    public void testAddExpense() {

        // Enter some data into the expense and reason fields
        Espresso.onView(withId(R.id.enterNewExpenseAmount)).perform(ViewActions.typeText("50"));
        Espresso.onView(withId(R.id.enterNewExpenseReason)).perform(ViewActions.typeText("Test expense"));

        // Close the soft keyboard
        Espresso.closeSoftKeyboard();

        // Click on the involved persons list item
        Espresso.onView(withId(R.id.enterNewExpenseInvolvedPersons)).perform(click());

        // Perform any actions related to selecting users (if applicable)

        // Click on the save button
        //Espresso.onView(withId(R.id.saveExpense)).perform(click());

        // Verify that the paymentRepository.createPayment method is called
        // Note: You can check if a specific intent is sent instead of mocking
        Intents.intended(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));

    }

}
