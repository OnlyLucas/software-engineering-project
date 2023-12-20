package com.example.software_engineering_project.controller;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityRegisterScreenControllerTest {

    /**
     * Test rule to initialize and launch the {@link ActivityRegisterScreenController} before each test execution.
     * <p>
     * This rule utilizes the IntentsTestRule provided by Espresso to set up and launch the register screen activity
     * before executing each test method within the test class.
     * <p>
     * Example usage:
     * {@code @Rule
     * public IntentsTestRule<ActivityRegisterScreenController> mActivityRule =
     *         new IntentsTestRule<>(ActivityRegisterScreenController.class);}
     * <p>
     * The rule is annotated with @Rule, indicating that it's a JUnit rule used to manage the test environment,
     * ensuring the activity under test is properly initialized before the test methods are run.
     */
    @Rule
    public IntentsTestRule<ActivityRegisterScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityRegisterScreenController.class);

    /**
     * Test method to verify the functionality of the cancel button on the register screen.
     * <p>
     * This test performs a click action on the "Cancel" button in the register screen to simulate a user interaction.
     * It then verifies whether the correct activity (ActivityLoginScreenController) is launched after clicking the cancel button.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testcancelButton() {
     *     Espresso.onView(ViewMatchers.withId(R.id.cancelButtonRegister)).perform(ViewActions.click());
     *     Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
     * }}
     * <p>
     * The test checks whether clicking the cancel button navigates the user back to the login screen (ActivityLoginScreenController).
     */
    @Test
    public void testcancelButton() {

        // Klick auf den Login-Button
        Espresso.onView(ViewMatchers.withId(R.id.cancelButtonRegister)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));

    }

    /**
     * Test method to verify the functionality of the register button on the register screen.
     * <p>
     * This test performs a click action on the "Register" button in the register screen to simulate a user registration process.
     * It then verifies whether the correct activity (ActivityLoginScreenController) is launched after the registration.
     * <p>
     * Example usage:
     * {@code @Test
     * public void testRegisterButtonRegister() {
     *     Espresso.onView(withId(R.id.registerButtonRegister)).perform(ViewActions.click());
     *     Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
     * }}
     * <p>
     * The test checks whether clicking the register button initiates the user registration process and then navigates to the login screen (ActivityLoginScreenController).
     */
    @Test
    public void testRegisterButtonRegister() {

        // Klick auf den Register-Button
        Espresso.onView(withId(R.id.registerButtonRegister)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));

    }

}