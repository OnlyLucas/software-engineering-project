package com.example.software_engineering_project.controller.groceries;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FragmentGroceryListHistoryControllerTest {

    @Before
    public void launchFragment() {
        // Launch the activity
        ActivityScenario<ActivityMainScreenController> scenario = ActivityScenario.launch(ActivityMainScreenController.class);

        scenario.onActivity(activity -> {
            Fragment fragment = new FragmentGroceryListController();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFragmentMainScreen, fragment); // replace with your fragment container ID
            transaction.commit();
        });


    }

    private void createTestData() {
        //Ein TestElemente erstellen
        Espresso.onView(withId(R.id.input))
                .perform(ViewActions.typeText("TestItemHistory"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enter)).perform(ViewActions.click());
    }

    private void deleteTestData(){

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .onChildView(withId(R.id.removeGroceryListHistory))
                .perform(ViewActions.click());

    }

    private void moveTestDataToHistory() {

        //Element in Historie verschieben
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryList))
                .atPosition(0)
                .onChildView(withId(R.id.uncheckedGroceryList))
                .perform(ViewActions.click());

    }

    @Test
    public void moveToHistory() {

        createTestData();

        //Element in Historie verschieben
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryList))
                .atPosition(0)
                .onChildView(withId(R.id.uncheckedGroceryList))
                .perform(ViewActions.click());

        deleteTestData();

    }

    @Test
    public void ydeleteFromHistoryButton() {

        createTestData();
        moveTestDataToHistory();

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .onChildView(withId(R.id.removeGroceryListHistory))
                .perform(ViewActions.click());

    }

    @Test
    public void zDeleteFromHistory() {

        createTestData();
        moveTestDataToHistory();

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .perform(ViewActions.longClick());

    }

}