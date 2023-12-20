package com.example.software_engineering_project.controller.cleanings;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FragmentCleaningPlanAddControllerTest {

    /**
     * Prepares the test environment by logging into the application and initializing a specific fragment.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentCleaningPlanController} within {@link ActivityMainScreenController}.</li>
     *     <li>Performing a click action on the "Add Cleaning Plan" button within the fragment.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the specified fragment within
     * the {@link ActivityMainScreenController} is accessible after a successful login.
     * The method assumes the existence of the specified fragment and a valid navigation flow from
     * the login screen to the {@link ActivityMainScreenController}.
     * <p>
     * This setup method prepares the testing environment by performing necessary actions such as login,
     * launching a fragment, and triggering a UI action for subsequent test execution.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentCleaningPlanController fragment = new FragmentCleaningPlanController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragment, R.id.contentFragmentMainScreen);

        Espresso.onView(withId(R.id.addCleaningPlan)).perform(ViewActions.click());

    }

    /**
     * Verifies the launch of a specific fragment within the Activity.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Waits for a specified time to ensure the visibility of UI elements.</li>
     *     <li>Checks if the specified fragment with the tag "fragment_cleaning_plan_add" is displayed.</li>
     * </ol>
     * <p>
     * Preconditions: The application must have successfully launched the specified fragment
     * within the {@link ActivityMainScreenController}.
     * <p>
     * This test method aims to ensure the correct launch and display of the specified fragment
     * within the activity layout.
     */
    @Test
    public void testAFragmentLaunch() {

        // Wait for some time to ensure the saveExpense button is visible
        TestUtils.waitingTime();

        // Check if the fragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentCleaningPlan)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_cleaning_plan_add")))));

    }


    @Test
    public void testBOpenAndSelectDateRangePicker() {

        // Klicke auf die Schaltfläche zum Öffnen des DateRangePickers
        Espresso.onView(ViewMatchers.withId(R.id.datePickerCleaningPlan)).perform(ViewActions.click());

        // Warte, bis der DateRangePicker angezeigt wird
        TestUtils.waitingTime();

        // Klicke auf das Datum im Material DateRangePicker anhand seines Texts
        // Hier klicken wir auf das Datum "7. Februar 2024" als Beispiel
        //Espresso.onData(allOf(withText("7"),withParent(ViewMatc)))
        //        .perform(ViewActions.click());

        // Warte auf die Anzeige des "OK" oder "Speichern" Buttons
        // Hier kannst du die Textressource anpassen, die auf dem Button angezeigt wird
        Espresso.onView(withText(R.string.save)).perform(ViewActions.click());

    }

    /**
     * Tests the selection functionality of a Spinner component.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicks on the Spinner to open the dropdown list.</li>
     *     <li>Selects an item from the dropdown list.</li>
     *     <li>Verifies if the Spinner displays the selected item after the selection.</li>
     * </ol>
     * <p>
     * Preconditions: The Spinner component with the specified ID must be accessible and contain selectable items.
     * The method assumes the existence of a valid Spinner component within the application's layout.
     * <p>
     * This test method aims to validate the correct functionality of the Spinner component by testing
     * the selection of an item from the dropdown and ensuring its display within the Spinner after selection.
     */
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

    /**
     * Tests the functionality of the "Save CleaningPlan" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Performs necessary actions needed for testing, such as entering data into input fields.</li>
     *     <li>Invokes the {@link #testCSpinnerSelection()} method to select an item in a Spinner component.</li>
     *     <li>Verifies if the "Save CleaningPlan" button is visible and clickable.</li>
     *     <li>Clicks on the "Save CleaningPlan" button.</li>
     * </ol>
     * <p>
     * Preconditions: The application must display the screen containing the "Save CleaningPlan" button,
     * and required input fields must be accessible for data entry.
     * The method assumes the existence of the specified button and input fields within the application's layout.
     * <p>
     * This test method aims to validate the correct functionality of the "Save CleaningPlan" button
     * by checking its visibility, clickability, and performing a click action.
     */
    @Test
    public void testDSaveButtonClick() {

        // Perform any actions needed for testing
        Espresso.onView(withId(R.id.enterNameAddCleaningPlan))
                .perform(ViewActions.typeText("TestItem"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.enterDescriptionCleaningPlan))
                .perform(ViewActions.typeText("TestDescription"), ViewActions.closeSoftKeyboard());

        testCSpinnerSelection();

        // Wait for some time to ensure the saveExpense button is visible
        TestUtils.waitingTime();

        // Überprüft, ob der "Save CleaningPlan" Button sichtbar und klickbar ist
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.saveCleaningPlan)).check(matches(isClickable()));

        // Click on the save button
        Espresso.onView(withId(R.id.saveCleaningPlan))
                .perform(click());

    }

}