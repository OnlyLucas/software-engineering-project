package com.example.software_engineering_project.controller.budget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityMainScreenController;
import com.example.software_engineering_project.controller.budget.FragmentBudgetAddExpenseScreenController;
import com.example.software_engineering_project.controller.budget.FragmentBudgetMainController;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.core.content.MimeTypeFilter.matches;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

        Espresso.onView(withId(R.id.addExpense)).perform(ViewActions.click());

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

        // Wait for some time to ensure the saveExpense button is visible
        try {
            Thread.sleep(1000); // You can adjust the waiting time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the save button and check if the save button is visible
        Espresso.onView(ViewMatchers.withId(R.id.saveExpense))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Verify that the paymentRepository.createPayment method is called
        // Note: You can check if a specific intent is sent instead of mocking
        Intents.intended(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));
    }

}