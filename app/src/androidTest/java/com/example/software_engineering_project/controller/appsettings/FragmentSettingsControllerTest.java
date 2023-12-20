package com.example.software_engineering_project.controller.appsettings;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentSettingsControllerTest {

    /**
     * Rule to facilitate testing of {@link ActivityLoginScreenController} using Espresso Intents.
     * <p>
     * This rule is used to launch and handle the lifecycle of the {@link ActivityLoginScreenController}
     * for testing purposes using {@link IntentsTestRule} provided by Espresso.
     * <p>
     * Preconditions: The {@link ActivityLoginScreenController} must be a valid activity intended for testing.
     * <p>
     * This rule enables the setup and teardown of the activity lifecycle, allowing the execution of Espresso
     * test cases within the context of the {@link ActivityLoginScreenController} to test its UI interactions
     * and Intent interactions with other activities.
     */
    @Rule
    public IntentsTestRule<ActivityLoginScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityLoginScreenController.class);

    /**
     * Initializes the test environment before executing individual test cases.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentSettingsController} within {@link ActivityMainScreenController}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the user has access to the settings fragment.
     * The method assumes the existence of the specified UI elements within the {@link FragmentSettingsController}.
     * <p>
     * This setup method is responsible for initializing the test environment by performing necessary actions,
     * such as logging in and launching the specific fragment required for testing.
     */
    @Before
    public void setUp() {
        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentSettingsController fragmentSettingsController = new FragmentSettingsController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentSettingsController, R.id.contentFragmentMainScreen);

    }

    /**
     * Validates the functionality of the "Change Password" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicking on the "Change Password" button with the ID {@code R.id.changePasswordButton}.</li>
     *     <li>Verifying whether the {@link FragmentChangePasswordController} view is displayed.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the "Change Password" button is visible and clickable.
     * The method assumes the existence of the specified UI elements and the availability of the corresponding
     * {@link FragmentChangePasswordController}.
     * <p>
     * This test method aims to confirm that clicking the "Change Password" button correctly displays the
     * {@link FragmentChangePasswordController} view.
     */
    @Test
    public void testChangePasswordButton() {
        // Klicke auf den Change Password Button
        Espresso.onView(withId(R.id.changePasswordButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentChangePasswordController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_change_password")))));
    }

    /**
     * Validates the functionality of the "Change Mail" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicking on the "Change Mail" button with the ID {@code R.id.changeMailButton}.</li>
     *     <li>Verifying whether the {@link FragmentChangeMailController} view is displayed.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the "Change Mail" button is visible and clickable.
     * The method assumes the existence of the specified UI elements and the availability of the corresponding
     * {@link FragmentChangeMailController}.
     * <p>
     * This test method aims to confirm that clicking the "Change Mail" button correctly displays the
     * {@link FragmentChangeMailController} view.
     */
    @Test
    public void testChangeMailButton() {
        // Klicke auf den Change Mail Button
        Espresso.onView(withId(R.id.changeMailButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentChangeMailController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_change_mail")))));
    }

    /**
     * Validates the functionality of the "Create Flat Share" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicking on the "Create Flat Share" button with the ID {@code R.id.manageFlatShareButton}.</li>
     *     <li>Verifying whether the {@link FragmentManageFlatShareController} view is displayed.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the "Create Flat Share" button is visible and clickable.
     * The method assumes the existence of the specified UI elements and the availability of the corresponding
     * {@link FragmentManageFlatShareController}.
     * <p>
     * This test method aims to confirm that clicking the "Create Flat Share" button correctly displays the
     * {@link FragmentManageFlatShareController} view.
     */
    @Test
    public void testCreateFlatShareButton() {
        // Klicke auf den Create Flat Share Button
        Espresso.onView(withId(R.id.manageFlatShareButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentManageFlatShareController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_manage_flat_share")))));
    }

    /**
     * Validates the functionality of the "Log Out" button.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicking on the "Log Out" button with the ID {@code R.id.logOutButton}.</li>
     *     <li>Verifying whether the {@link ActivityLoginScreenController} is launched after the log out action.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the user is logged in and the "Log Out" button is visible and clickable.
     * The method assumes the existence of the specified UI elements and the availability of the log out functionality.
     * <p>
     * This test method aims to confirm that clicking the "Log Out" button correctly navigates to the
     * {@link ActivityLoginScreenController} after the log out action.
     */
    @Test
    public void testLogOutButton() {
        // Klicke auf den Log Out Button
        Espresso.onView(withId(R.id.logOutButton)).perform(ViewActions.click());

        // Überprüfe, ob die ActivityLoginScreenController gestartet wird
        Intents.intending(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));

        // Test if ActivityLoginScreenController is launched
        Intents.intending(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
    }
}
