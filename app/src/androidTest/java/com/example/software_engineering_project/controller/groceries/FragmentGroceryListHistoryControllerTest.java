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
import com.example.software_engineering_project.TestUtils;
import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.controller.ActivityMainScreenController;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FragmentGroceryListHistoryControllerTest {

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
     * Creates test data by adding an item to the grocery list.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Enters a test item ("TestItemHistory") into the input field.</li>
     *     <li>Clicks the "Enter" button to add the test item to the grocery list.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have an input field to add items to the grocery list.
     * The method assumes successful addition of the test item to the list after clicking the "Enter" button.
     * <p>
     * This method aims to create test data by adding a specific item to the grocery list for subsequent test cases.
     */
    private void createTestData() {

        //Ein TestElemente erstellen
        Espresso.onView(withId(R.id.input))
                .perform(ViewActions.typeText("TestItemHistory"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.enter)).perform(ViewActions.click());

    }

    /**
     * Deletes test data by removing an item from the grocery list history.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Navigates to the grocery list history.</li>
     *     <li>Waits for a specified time.</li>
     *     <li>Removes the first item from the grocery list history.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have a way to navigate to the grocery list history.
     * The method assumes the existence of the grocery list history and the ability to remove items from it.
     * <p>
     * This method aims to delete test data by removing an item from the grocery list history for test cleanup purposes.
     */
    private void deleteTestData(){

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        TestUtils.waitingTime();

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .onChildView(withId(R.id.removeGroceryListHistory))
                .perform(ViewActions.click());

    }

    /**
     * Moves test data to the grocery list history by performing a specific action on an item.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Selects an item from the grocery list.</li>
     *     <li>Performs an action to move the selected item to the grocery list history.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have a way to interact with items in the grocery list.
     * The method assumes the ability to perform actions to move items from the grocery list to its history.
     * <p>
     * This method aims to move specific test data to the grocery list history by performing a particular action
     * on an item in the list.
     */
    private void moveTestDataToHistory() {

        //Element in Historie verschieben
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryList))
                .atPosition(0)
                .onChildView(withId(R.id.uncheckedGroceryList))
                .perform(ViewActions.click());

    }

    /**
     * Tests the functionality of moving an item from the current grocery list to its history.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Creates test data by adding an item to the grocery list.</li>
     *     <li>Selects an item from the grocery list.</li>
     *     <li>Performs an action to move the selected item to the grocery list history.</li>
     *     <li>Deletes the test data by removing the item from the grocery list history.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have functionalities to add items to the grocery list, move items to its history,
     * and delete items from the grocery list history.
     * The method assumes the existence of these functionalities for testing purposes.
     * <p>
     * This test method aims to validate the correct behavior of moving an item from the grocery list to its history
     * and confirms the successful movement by deleting the moved item from the history.
     */
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

    /**
     * Tests the functionality of deleting an item from the grocery list history.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Creates test data by adding an item to the grocery list.</li>
     *     <li>Moves the test data to the grocery list history.</li>
     *     <li>Navigates to the grocery list history.</li>
     *     <li>Waits for a specified time.</li>
     *     <li>Deletes the first item from the grocery list history.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have functionalities to add items to the grocery list, move items to its history,
     * navigate to the history, and delete items from the grocery list history.
     * The method assumes the existence of these functionalities for testing purposes.
     * <p>
     * This test method aims to validate the correct behavior of deleting an item from the grocery list history
     * after moving it from the current grocery list to the history.
     */
    @Test
    public void yDeleteFromHistoryButton() {

        createTestData();
        moveTestDataToHistory();

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        TestUtils.waitingTime();

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .onChildView(withId(R.id.removeGroceryListHistory))
                .perform(ViewActions.click());

    }

    /**
     * Tests the functionality of deleting an item from the grocery list history.
     * <p>
     * Test steps include:
     * <ol>
     *     <li>Creates test data by adding an item to the grocery list.</li>
     *     <li>Moves the test data to the grocery list history.</li>
     *     <li>Navigates to the grocery list history.</li>
     *     <li>Waits for a specified time.</li>
     *     <li>Deletes the first item from the grocery list history by performing a long click.</li>
     * </ol>
     * <p>
     * Preconditions: The UI must have functionalities to add items to the grocery list, move items to its history,
     * navigate to the history, and perform long clicks to delete items from the grocery list history.
     * The method assumes the existence of these functionalities for testing purposes.
     * <p>
     * This test method aims to validate the correct behavior of deleting an item from the grocery list history
     * after moving it from the current grocery list to the history by performing a long click action.
     */
    @Test
    public void zDeleteFromHistory() {

        createTestData();
        moveTestDataToHistory();

        //In History gehen
        Espresso.onView(withId(R.id.historyGroceryList)).perform(ViewActions.click());

        TestUtils.waitingTime();

        //Element löschen
        Espresso.onData(Matchers.anything())
                .inAdapterView(withId(R.id.groceryListHistory))
                .atPosition(0)
                .perform(ViewActions.longClick());

    }

}