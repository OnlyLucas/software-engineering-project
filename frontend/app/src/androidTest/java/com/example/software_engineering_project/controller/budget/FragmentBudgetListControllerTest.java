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

    /**
     * Prepares the test environment by initiating the application login and launching a specific fragment.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentBudgetMainController} within {@link ActivityMainScreenController}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the specified fragment within
     * the {@link ActivityMainScreenController} is accessible after a successful login.
     * The method assumes the existence of the specified fragment and a valid navigation flow from
     * the login screen to the {@link ActivityMainScreenController}.
     * <p>
     * This setup method prepares the testing environment by performing necessary actions such as login
     * and fragment launching for subsequent test execution.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentBudgetMainController fragmentBudgetMainController = new FragmentBudgetMainController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentBudgetMainController, R.id.contentFragmentMainScreen);

    }

    /**
     * Validates the visibility of a specific fragment within the layout by checking the presence of a view element.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Verifying the visibility of the fragment by checking the existence of a View element
     *     with the specified tag value within the layout identified by {@code R.id.contentFragmentBudgetMain}.</li>
     * </ol>
     * <p>
     * Preconditions: The fragment with the specified tag value must be present and visible within the layout
     * identified by {@code R.id.contentFragmentBudgetMain}.
     * <p>
     * This test method aims to confirm the visibility of the fragment within the layout by checking for the presence
     * of a specific view element associated with the fragment's tag value.
     */
    @Test
    public void testFragmentVisibility() {

        // Überprüft, ob das Fragment sichtbar ist, indem es ein View-Element aus dem Fragment überprüft
        Espresso.onView(withId(R.id.contentFragmentBudgetMain))
                .check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_budget_list")))));

    }

}