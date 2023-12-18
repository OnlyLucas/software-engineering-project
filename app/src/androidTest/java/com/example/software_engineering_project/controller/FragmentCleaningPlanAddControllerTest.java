package com.example.software_engineering_project.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterSpinnerList;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanAddController;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanController;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import java.util.Calendar;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FragmentCleaningPlanAddControllerTest {

    @Rule
    public IntentsTestRule<ActivityMainScreenController> activity = new IntentsTestRule<>(ActivityMainScreenController.class);

    @Before
    public void launchFragment() {
        // Launch the activity
        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentCleaningPlanController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });
        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentCleaningPlanAddController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentCleaningPlan, fragment); // replace with your fragment container ID
            transaction.commit();
        });
    }

    @Test
    public void testAFragmentLaunch() {

        // Wait for some time to ensure the saveExpense button is visible
        try {
            Thread.sleep(2000); // You can adjust the waiting time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the fragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentCleaningPlan)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_cleaning_plan_add")))));

    }

    @Test
    public void testBOpenAndSelectDateRangePicker() {

        // Click on the button or view that opens the date range picker
        Espresso.onView(ViewMatchers.withId(R.id.datePickerCleaningPlan)).perform(click());

        // Check if the date range picker is displayed (optional)
        Espresso.onView(ViewMatchers.withId(R.id.datePickerCleaningPlan)).check(matches(isDisplayed()));

        // Get the current date
        Calendar currentDate = Calendar.getInstance();
        Date start = currentDate.getTime();

        // Open Material Date Range Picker
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(MaterialDatePicker.class.getName())))
                .perform(PickerActions.setDate(start.getYear(), start.getMonth(), start.getDay()),
                        PickerActions.setDate(2024, 1, 31));

        // Click on the "OK" button
        Espresso.onView(ViewMatchers.withText(R.string.save)).perform(click());

    }

    @Test
    public void testCSpinnerSelection() {

        // Click the spinner to open the dropdown
        Espresso.onView(withId(R.id.chooseInterval)).perform(ViewActions.click());

        // Select an item from the dropdown
        Espresso.onData(CoreMatchers.instanceOf(AdapterSpinnerList.class))
                .atPosition(1) // Replace with the position of the item you want to select
                .perform(ViewActions.click());

        // Check if the spinner now displays the selected item
        Espresso.onView(withId(R.id.chooseInterval))
                .check(matches(withSpinnerText(R.string.bi_weekly)));

    }

    @Test
    public void testDSaveButtonClick() {

        // Perform any actions needed for testing
        Espresso.onView(withId(R.id.enterNameAddCleaningPlan))
                .perform(ViewActions.typeText("TestItem"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.enterDescriptionCleaningPlan))
                .perform(ViewActions.typeText("TestDescription"), ViewActions.closeSoftKeyboard());

        //testBOpenAndSelectDateRangePicker();
        //testCSpinnerSelection();

        // Wait for some time to ensure the saveExpense button is visible
        try {
            Thread.sleep(1000); // You can adjust the waiting time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Überprüft, ob der "Save CleaningPlan" Button sichtbar und klickbar ist
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(isClickable()));

        // Click on the save button
        Espresso.onView(withId(R.id.saveCleaningPlan)) // Replace with your save button ID
                .perform(click());

    }

}

