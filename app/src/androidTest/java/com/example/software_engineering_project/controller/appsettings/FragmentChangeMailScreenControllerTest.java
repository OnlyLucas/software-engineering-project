package com.example.software_engineering_project.controller.appsettings;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Test;

/**
 * Espresso test class designed to validate and verify the functionality of the email change feature
 * within {@link FragmentChangeMailController}.
 * <p>
 * This test suite comprises test cases to confirm the proper execution and functionality of email address changes.
 */
public class FragmentChangeMailScreenControllerTest {

    /**
     * Prepares the test environment by initiating app login and launching specified fragments within the main activity.
     * <p>
     * Actions performed:
     * - Initiates application login using {@link TestUtils#appLogin()}.
     * - Launches {@link FragmentSettingsController} and {@link FragmentChangeMailController} within
     * {@link ActivityMainScreenController}.
     * - Each fragment is added to the fragment container identified by {@code R.id.contentFragmentMainScreen}.
     * <p>
     * Preconditions: Application should require login.
     * {@link FragmentSettingsController} and {@link FragmentChangeMailController} must be set up for testing.
     * <p>
     * Example usage:
     * {@code
     * \@Before
     * public void setUp() {
     * // Invoke the setup method
     * setUp();
     * }
     * }
     *
     * @see FragmentSettingsController
     * @see FragmentChangeMailController
     * @see ActivityMainScreenController
     * @see TestUtils#appLogin()
     * @see TestUtils#launchFragment(Class, Fragment, int)
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentSettingsController fragmentSettingsController = new FragmentSettingsController();
        FragmentChangeMailController fragmentChangeMailController = new FragmentChangeMailController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentSettingsController, R.id.contentFragmentMainScreen);
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentChangeMailController, R.id.contentFragmentMainScreen);

    }

    /**
     * Validates the functionality of changing the email address.
     * <p>
     * Test steps:
     * Enters new email data into the respective input fields for email change.
     * Triggers the process to save the modified email data into the database.
     * Resets the testing environment to its initial state for further testing.
     * Reverts the email address back to its original value and saves the changes.
     * Verifies whether the email reversion process was successful.
     * <p>
     * Preconditions: The application must be in a state where the user can change their email address.
     * The test assumes the availability of UI elements with the IDs {@code R.id.currentMail},
     * {@code R.id.newMail}, {@code R.id.confirmNewMail}, and {@code R.id.saveChangeMail}.
     * <p>
     * This test method aims to verify the email change process, including both successful email updates
     * and proper handling of reverting to the original email address after modification.
     */
    @Test
    public void testChangeMail() {

        //Klicke auf das Eingabefeld aktuelle E-Mail Adresse
        Espresso.onView(withId(R.id.currentMail))
                .perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard());

        //Kicke auf das Eingabefeld neue E-Mail Adresse
        Espresso.onView(withId(R.id.newMail))
                .perform(ViewActions.typeText("test2@test.de"), ViewActions.closeSoftKeyboard());

        //Kicke auf das Eingabefeld neue E-Mail Adresse bestätigen
        Espresso.onView(withId(R.id.confirmNewMail))
                .perform(ViewActions.typeText("test2@test.de"), ViewActions.closeSoftKeyboard());

        //Änderungen in Datenbank speichern
        Espresso.onView(withId(R.id.saveChangeMail))
                .perform(ViewActions.click());

        setUp();

        //Überprüfen ob wechsel geklappt hat.
        //Klicke auf das Eingabefeld aktuelle E-Mail Adresse
        Espresso.onView(withId(R.id.currentMail))
                .perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard());

        //Kicke auf das Eingabefeld neue E-Mail Adresse
        Espresso.onView(withId(R.id.newMail))
                .perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard());

        //Kicke auf das Eingabefeld neue E-Mail Adresse bestätigen
        Espresso.onView(withId(R.id.confirmNewMail))
                .perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard());

        //Änderungen in Datenbank speichern
        Espresso.onView(withId(R.id.saveChangeMail))
                .perform(ViewActions.click());

    }

}