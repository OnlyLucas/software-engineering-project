package com.example.software_engineering_project.controller.appsettings;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

@RunWith(AndroidJUnit4.class)
public class FragmentSettingsControllerTest {

    @Rule
    public IntentsTestRule<ActivityLoginScreenController> mActivityRule =
            new IntentsTestRule<>(ActivityLoginScreenController.class);

    @Before
    public void launchFragment() {
        // Launch the activity
        ActivityScenario<ActivityLoginScreenController> scenarioLogin = ActivityScenario.launch(ActivityLoginScreenController.class);

        Espresso.onView(withId(R.id.enterLoginEmail)).perform(ViewActions.typeText("jane.doe@example.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enterLoginPassword)).perform(ViewActions.typeText("password2"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentSettingsController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });
    }

    @Test
    public void testChangePasswordButton() {
        // Klicke auf den Change Password Button
        Espresso.onView(withId(R.id.changePasswordButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentChangePasswordController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_change_password")))));
    }

    @Test
    public void testChangeMailButton() {
        // Klicke auf den Change Mail Button
        Espresso.onView(withId(R.id.changeMailButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentChangeMailController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_change_mail")))));
    }

    @Test
    public void testCreateFlatShareButton() {
        // Klicke auf den Create Flat Share Button
        Espresso.onView(withId(R.id.manageFlatShareButton)).perform(ViewActions.click());

        // Überprüfe, ob die FragmentManageFlatShareController-View angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.contentFragmentMainScreen)).check(ViewAssertions.matches(hasDescendant(withTagValue(org.hamcrest.Matchers.is("fragment_manage_flat_share")))));
    }

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
