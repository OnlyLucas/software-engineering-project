package com.example.software_engineering_project.controller.budget;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentBudgetListControllerTest {

    @Before
    public void launchFragment() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentBudgetMainController, R.id.contentFragmentMainScreen);

    }

    @Test
    public void testFragmentVisibility() {

        // Überprüft, ob das Fragment sichtbar ist, indem es ein View-Element aus dem Fragment überprüft
        Espresso.onView(withId(R.id.contentFragmentBudgetMain))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_budget_list")))));

    }

}