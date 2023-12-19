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
import com.example.software_engineering_project.controller.groceries.FragmentGroceryListController;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
public class ActivityMainScreenControllerTest {

    @Rule
    public IntentsTestRule<ActivityMainScreenController> mActivityRule = new IntentsTestRule<>(ActivityMainScreenController.class);

    @Before
    public void launchFragment() {
        ActivityScenario<ActivityLoginScreenController> scenarioLogin = ActivityScenario.launch(ActivityLoginScreenController.class);
        Espresso.onView(withId(R.id.enterLoginEmail)).perform(ViewActions.typeText("jane.doe@example.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterLoginPassword)).perform(ViewActions.typeText("password2"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentGroceryListController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });

    }

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
