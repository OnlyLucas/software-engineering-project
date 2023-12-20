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

    /**
     * Prepares the test environment by logging into the application and initializing a specific fragment.
     * <p>
     * Setup actions include:
     * <ol>
     *     <li>Initiating the application login process using {@link TestUtils#appLogin()}.</li>
     *     <li>Launching the {@link FragmentGroceryListController} within {@link ActivityMainScreenController}.</li>
     * </ol>
     * <p>
     * Preconditions: The application must be in a state where the specified fragment within
     * the {@link ActivityMainScreenController} is accessible after a successful login.
     * The method assumes the existence of the specified fragment and a valid navigation flow from
     * the login screen to the {@link ActivityMainScreenController}.
     * <p>
     * This setup method prepares the testing environment by performing necessary actions such as login
     * and launching a fragment for subsequent test execution.
     */
    @Before
    public void setUp() {

        // Launch the activity
        TestUtils.appLogin();

        //Start the fragment
        FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
        TestUtils.launchFragment(ActivityMainScreenController.class, fragmentGroceryListController, R.id.contentFragmentMainScreen);

    }

    /**
     * Tests the functionality of adding an item to the grocery list.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Clicks on the input field and adds a test item to the grocery list.</li>
     *     <li>Waits for a specified time to ensure the added item is displayed in the list.</li>
     * </ol>
     * <p>
     * Preconditions: The application must have a visible input field for adding items to the grocery list.
     * The method assumes successful addition of an item to the list results in its display within the UI.
     * <p>
     * This test method verifies the correctness of adding an item to the grocery list by checking its display
     * in the list after insertion.
     */
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

    /**
     * Tests the functionality of removing an item from the grocery list.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Waits for a specified time.</li>
     *     <li>Performs a long click on the first element in the list to remove it.</li>
     *     <li>Verifies that the removed item is no longer displayed in the list.</li>
     * </ol>
     * <p>
     * Preconditions: The grocery list must contain at least one element for removal.
     * The method assumes a long click on the first item will result in its removal from the list.
     * <p>
     * This test method validates the correctness of removing an item from the grocery list by checking its absence
     * from the list after the removal operation.
     */
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

    //TODO ADD JAVA DOC
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
