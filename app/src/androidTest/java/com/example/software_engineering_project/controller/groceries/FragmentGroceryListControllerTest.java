package com.example.software_engineering_project.controller.groceries;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanController;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FragmentGroceryListControllerTest {

    @Before
    public void launchFragment() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentGroceryListController, R.id.contentFragmentMainScreen);

    }

    @Test
    public void testAddItemToGroceryList() {
        // Klicke auf das Eingabefeld und füge einen Artikel hinzu
        Espresso.onView(withId(R.id.input))
                .perform(ViewActions.typeText("TestItem"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enter)).perform(ViewActions.click());

        TestUtils.waitingTime();

        // Warte auf die Anzeige des hinzugefügten Elements in der Liste
        Espresso.onView(withText("TestItem")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        
    }

    @Test
    public void testRemoveItemFromGroceryList() {
        // Voraussetzung: Es gibt mindestens ein Element in der Liste

        TestUtils.waitingTime();

        // Klicke lange auf das erste Element der Liste, um es zu entfernen
        Espresso.onData(Matchers.anything()).inAdapterView(withId(R.id.groceryList)).atPosition(0)
                .perform(ViewActions.longClick());

        // Überprüfe, ob das Element entfernt wurde, indem es nicht mehr in der Liste angezeigt wird
        Espresso.onView(withText("TestItem")).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testClickOnGroceryListItem() {
        // Voraussetzung: Es gibt mindestens ein Element in der Liste

        TestUtils.waitingTime();

        // Klicke auf das erste Element der Liste
        Espresso.onData(Matchers.anything()).inAdapterView(withId(R.id.groceryList)).atPosition(0)
                .perform(ViewActions.click());

        // Überprüfe, ob der korrekte Artikelname in einem Toast angezeigt wird
        // (Ersetze "expectedName" durch den erwarteten Namen)
        //TODO Espresso.onView(withText("expectedName")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
        //      .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
