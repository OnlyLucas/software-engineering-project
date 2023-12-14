package com.example.software_engineering_project;

import android.content.Context;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.software_engineering_project.controller.ActivityRegisterScreenController;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityRegisterScreenControllerTest {

    @Before
    public void launchActivity() {
        ActivityScenario.launch(ActivityRegisterScreenController.class);
    }

    @Test
    public void testCancelButton() {
        // Click on the cancel button
        Espresso.onView(withId(R.id.cancelButtonRegister)).perform(ViewActions.click());

        // Check if the login screen is displayed
        Espresso.onView(withId(R.layout.activity_login_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterButton() {
        // Enter some data into the registration fields
        Espresso.onView(withId(R.id.emailRegister)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(withId(R.id.firstNameRegister)).perform(ViewActions.typeText("John"));
        Espresso.onView(withId(R.id.surnameRegister)).perform(ViewActions.typeText("Doe"));
        Espresso.onView(withId(R.id.passwordRegister)).perform(ViewActions.typeText("password123"));

        // Close the soft keyboard
        Espresso.closeSoftKeyboard();

        // Click on the register button
        Espresso.onView(withId(R.id.registerButtonRegister)).perform(ViewActions.click());

        // Check if the login screen is displayed
        Espresso.onView(withId(R.layout.activity_login_screen)).check(matches(isDisplayed()));

        // Check if the toast message is displayed
        //Espresso.onView(withText(R.string.please_login_with_your_created_credentials))
        //        .inRoot(new ToastMatcher()) // Custom matcher for toast messages
        //       .check(matches(isDisplayed()));
    }
}

