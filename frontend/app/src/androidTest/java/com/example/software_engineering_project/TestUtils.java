package com.example.software_engineering_project;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;

public class TestUtils {

    /**
     * Delays the execution for a specified period of time.
     * <p>
     * This method pauses the execution for 2000 milliseconds (2 seconds) using {@link Thread#sleep(long)}.
     * It is primarily used to ensure a wait duration during UI interactions or visibility checks in test scenarios.
     */
    public static void waitingTime() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Simulates the login process within the application for testing purposes.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Launching the {@link ActivityLoginScreenController}.</li>
     *     <li>Entering the login email into the "Email" field with the ID {@code R.id.enterLoginEmail}.</li>
     *     <li>Entering the login password into the "Password" field with the ID {@code R.id.enterLoginPassword}.</li>
     *     <li>Clicking on the "Login" button with the ID {@code R.id.loginButton}.</li>
     *     <li>Waiting for a specified time using {@link TestUtils#waitingTime()} after login.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the login screen is accessible.
     * The method assumes the existence of the specified UI elements within the login screen.
     * <p>
     * This method simulates the login process within the application for test scenarios, allowing the tester
     * to automate the login sequence and proceed with further test steps after successful login.
     */
    public static void appLogin() {

        ActivityScenario<ActivityLoginScreenController> scenarioLogin = ActivityScenario.launch(ActivityLoginScreenController.class);

        Espresso.onView(withId(R.id.enterLoginEmail)).perform(ViewActions.typeText("jane.doe@example.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterLoginPassword)).perform(ViewActions.typeText("password2"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        waitingTime();

    }

    /**
     * Launches a fragment within an activity for testing purposes.
     * <p>
     * This method initiates an {@link ActivityScenario} for the specified {@code activityClass}.
     * It then performs a fragment transaction to replace the content identified by {@code fragmentContainerId}
     * with the provided {@code fragment} using the {@link FragmentTransaction} within the activity's
     * {@link androidx.fragment.app.FragmentManager}.
     * <p>
     * Preconditions: The provided {@code activityClass} must represent a valid AppCompatActivity.
     * The method assumes the existence of the specified {@code fragment} and a valid {@code fragmentContainerId}
     * within the activity's layout.
     * <p>
     * This method facilitates the launching of a specific fragment within an activity, enabling testers
     * to conduct UI and functionality tests on the specified fragment within the application.
     *
     * @param activityClass       The class reference of the AppCompatActivity where the fragment will be launched.
     * @param fragment            The fragment to be launched within the activity.
     * @param fragmentContainerId The ID of the container in the activity's layout where the fragment will be placed.
     */
    public static void launchFragment(Class<? extends AppCompatActivity> activityClass, Fragment fragment, int fragmentContainerId) {

        ActivityScenario.launch(activityClass).onActivity(activity -> {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(fragmentContainerId, fragment);
            transaction.commit();
        });

    }

}