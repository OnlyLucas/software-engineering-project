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

    public static void waitingTime() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void appLogin() {

        ActivityScenario<ActivityLoginScreenController> scenarioLogin = ActivityScenario.launch(ActivityLoginScreenController.class);

        Espresso.onView(withId(R.id.enterLoginEmail)).perform(ViewActions.typeText("jane.doe@example.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterLoginPassword)).perform(ViewActions.typeText("password2"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        waitingTime();

    }

    public static void launchFragment(Class<? extends AppCompatActivity> activityClass, Fragment fragment, int fragmentContainerId) {
        ActivityScenario.launch(activityClass).onActivity(activity -> {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(fragmentContainerId, fragment);
            transaction.commit();
        });
    }
}