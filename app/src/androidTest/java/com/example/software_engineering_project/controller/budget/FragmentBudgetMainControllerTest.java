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

    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentBudgetMainController, R.id.contentFragmentMainScreen);

    }

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

