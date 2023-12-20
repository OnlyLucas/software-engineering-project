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

    private Context context;

    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentCleaningPlanController fragmentCleaningPlanController = new FragmentCleaningPlanController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentCleaningPlanController, R.id.contentFragmentMainScreen);

    }

    @Test
    public void testFragmentUIElementsDisplayed() {
        Espresso.onView(withId(R.id.addCleaningPlan)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.goBackCleaningPlan)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

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
