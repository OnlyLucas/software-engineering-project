package com.example.software_engineering_project.controller;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;
import com.example.software_engineering_project.controller.ActivityRegisterScreenController;

@RunWith(AndroidJUnit4.class)
public class ActivityLoginScreenControllerTest {

    @Rule
    public IntentsTestRule<ActivityLoginScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityLoginScreenController.class);

    @Test
    public void testLoginButton() {
        // Klick auf den Login-Button
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityMainScreenController.class.getName()));
    }

    @Test
    public void testRegisterButtonLogin() {
        // Klick auf den Register-Button
        Espresso.onView(withId(R.id.registerButtonLogin)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityRegisterScreenController.class.getName()));
    }

    // Weitere Tests und Assertions können hier hinzugefügt werden, je nach den Anforderungen der Anwendung
}
