package com.example.software_engineering_project.controller.appsettings;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.junit.Before;
import org.junit.Test;

public class FragmentChangeMailScreenControllerTest {

    @Before
    public void launchFragment() {
        // Launch the activity
        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        // Use FragmentManager to add your fragment
        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentSettingsController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });

        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentChangeMailController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });
    }

    @Test
    public void testChangeMail() {

        //E-Mail Adresse ändern um zu checken ob wir sie zurück ändern können

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

        launchFragment();

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
