package com.example.software_engineering_project.controller;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivityLoginScreenControllerTest {

    /**
     * Rule used for UI testing with Espresso. It launches the {@link ActivityLoginScreenController} activity
     * to perform UI tests. This rule handles the activity lifecycle during testing, allowing interactions
     * and assertions to be made on the user interface.
     * <p>
     * The rule facilitates the setup and teardown of the activity under test, enabling UI tests to be
     * performed using Espresso's testing framework.
     */
    @Rule
    public IntentsTestRule<ActivityLoginScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityLoginScreenController.class);

    /**
     * Tests the functionality of the login button by performing actions on the login screen.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Enters the email and password into the respective fields on the login screen.</li>
     *     <li>Clicks on the login button.</li>
     *     <li>Waits for a specified time.</li>
     *     <li>Verifies that the correct activity (ActivityMainScreenController) is started after login.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must contain login screen elements such as email and password input fields,
     * a login button, and a navigational transition to ActivityMainScreenController upon successful login.
     * <p>
     * This test method validates the functionality of the login button by checking if the appropriate
     * activity is launched after successful login credentials are entered and the login button is clicked.
     */
    @Test
    public void testLoginButton() {

        Espresso.onView(withId(R.id.enterLoginEmail)).perform(ViewActions.typeText("jane.doe@example.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterLoginPassword)).perform(ViewActions.typeText("password2"), ViewActions.closeSoftKeyboard());

        // Klick auf den Login-Button
        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        TestUtils.waitingTime();

        //ActivityScenario<ActivityLoginScreenController> scenarioLogin = ActivityScenario.launch(ActivityLoginScreenController.class);

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));

    }

    /**
     * Tests the functionality of the register button on the login screen by clicking it.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicks on the register button in the login screen.</li>
     *     <li>Verifies that the correct activity (ActivityRegisterScreenController) is started after clicking the register button.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must contain a register button in the login screen and a navigational transition to
     * ActivityRegisterScreenController upon clicking the register button.
     * <p>
     * This test method validates the functionality of the register button by checking if the appropriate
     * activity is launched upon clicking the register button on the login screen.
     */
    @Test
    public void testRegisterButtonLogin() {
        // Klick auf den Register-Button
        Espresso.onView(withId(R.id.registerButtonLogin)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityRegisterScreenController.class.getName()));
    }

}
