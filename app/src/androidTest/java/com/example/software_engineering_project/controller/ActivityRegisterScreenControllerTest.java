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

    @Rule
    public IntentsTestRule<ActivityRegisterScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityRegisterScreenController.class);

    @Test
    public void testcancelButton() {
        // Klick auf den Login-Button
        Espresso.onView(ViewMatchers.withId(R.id.cancelButtonRegister)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
    }

    @Test
    public void testRegisterButtonRegister() {
        // Klick auf den Register-Button
        Espresso.onView(withId(R.id.registerButtonRegister)).perform(ViewActions.click());

        // Überprüfen, ob die richtige Activity gestartet wurde
        Intents.intended(IntentMatchers.hasComponent(ActivityLoginScreenController.class.getName()));
    }

}