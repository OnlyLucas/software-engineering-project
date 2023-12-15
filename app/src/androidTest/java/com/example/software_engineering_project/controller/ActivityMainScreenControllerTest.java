package com.example.software_engineering_project.controller;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityMainScreenControllerTest {

    @Rule
    public IntentsTestRule<ActivityMainScreenController> mActivityRule = new IntentsTestRule<>(ActivityMainScreenController.class);


    @Test
    public void testGroceryListButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.groceryListButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_grocery_list")))));

    }

    @Test
    public void testSettingsButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.goSettingsButtons)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_setting_screen")))));

    }

    @Test
    public void testCleaningPlanButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.cleaningPlanButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_cleaning_plan")))));

    }

    @Test
    public void testBudgetButton() {

        // Klick auf den "Grocery List" Button
        Espresso.onView(ViewMatchers.withId(R.id.budgetButton)).perform(click());

        // Hier überprüfen wir, ob das Fragment in der FragmentContainerView geladen wird.
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_budget_main")))));

    }

    // Weitere Tests und Assertions können hier hinzugefügt werden, je nach den Anforderungen der Anwendung
}
