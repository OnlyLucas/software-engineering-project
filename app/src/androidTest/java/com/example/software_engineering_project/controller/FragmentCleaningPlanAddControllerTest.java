package com.example.software_engineering_project.controller;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.EnumSet.allOf;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanAddController;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanController;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FragmentCleaningPlanAddControllerTest {


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
        Espresso.onView(withId(R.id.addCleaningPlan)).perform(ViewActions.click());
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

        // Klicke auf die Schaltfläche zum Öffnen des DateRangePickers
        Espresso.onView(ViewMatchers.withId(R.id.datePickerCleaningPlan)).perform(ViewActions.click());

        // Warte, bis der DateRangePicker angezeigt wird (hier kannst du eine angemessene Wartezeit festlegen)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Klicke auf das Datum im Material DateRangePicker anhand seines Texts
        // Hier klicken wir auf das Datum "7. Februar 2024" als Beispiel
        Espresso.onData(allOf(withText("7"),withParent(ViewMatc)))
                .perform(ViewActions.click());

        // Warte auf die Anzeige des "OK" oder "Speichern" Buttons
        // Hier kannst du die Textressource anpassen, die auf dem Button angezeigt wird
        Espresso.onView(withText(R.string.save)).perform(ViewActions.click());


    }

    @Test
    public void testCSpinnerSelection() {

        // Click the spinner to open the dropdown
        Espresso.onView(withId(R.id.chooseInterval)).perform(ViewActions.click());

        // Select an item from the dropdown
        Espresso.onData(CoreMatchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(1)
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
        testCSpinnerSelection();

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

