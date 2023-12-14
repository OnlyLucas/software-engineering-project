package com.example.software_engineering_project.controller;

import android.content.Intent;
import android.os.Looper;
import android.widget.Button;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import com.example.software_engineering_project.R;

@RunWith(RobolectricTestRunner.class)
public class ActivityLoginScreenControllerTest {

    private ActivityLoginScreenController activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(ActivityLoginScreenController.class).create().get();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testLoginButton() {
        Button loginButton = activity.findViewById(R.id.loginButton);
        assertNotNull("Login button is null", loginButton);

        // Trigger a click on the login button
        loginButton.performClick();

        // Allow the main looper to process any pending tasks
        shadowOf(Looper.getMainLooper()).idle();

        // Verify that the expected activity is started after clicking the login button
        //Intent expectedIntent = new Intent(activity, ActivityMainScreenController.class);
        //assertTrue(Robolectric.buildActivity(ActivityMainScreenController.class).get().isFinishing();
    }

    @Test
    public void testRegisterButton() {
        Button registerButton = activity.findViewById(R.id.registerButtonLogin);
        assertNotNull("Register button is null", registerButton);

        // Trigger a click on the register button
        registerButton.performClick();

        // Allow the main looper to process any pending tasks
        shadowOf(Looper.getMainLooper()).idle();

        // Verify that the expected activity is started after clicking the register button
        //Intent expectedIntent = new Intent(activity, ActivityRegisterScreenController.class);
        //assertTrue(Robolectric.buildActivity(ActivityRegisterScreenController.class).get().isFinishing());
    }

}

