package com.example.software_engineering_project.controller.groceries;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterGroceryListHistory;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.GroceryRepository;

import java.util.List;

public class FragmentGroceryListHistoryController extends Fragment {

    private static ListView listView;
    private static GroceryRepository groceryRepository;
    private static LiveData<List<GroupGrocery>> completedGroceryLiveData;
    private static ArrayAdapter<GroupGrocery> adapter;
    private static Context context;
    private ImageView goBack;
    private View fragmentView;

    /**
     * Removes a specific item (GroupGrocery) from the list of completed groceries and deletes it from the repository.
     *
     * @param item The index of the item to be removed from the list of completed groceries.
     */
    public static void removeItem(int item) {

        GroupGrocery grocery = completedGroceryLiveData.getValue().get(item);
        groceryRepository.deleteGroupGrocery(grocery, context);

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment for displaying the grocery list history.
     * Initializes necessary elements, observes LiveData for completed group groceries, and sets up the list view with an adapter.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     *                           This value may be null.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = requireActivity();
        groceryRepository = new GroceryRepository(context);
        completedGroceryLiveData = groceryRepository.getCompletedGroupGroceries();

        completedGroceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
            adapter = new AdapterGroceryListHistory(context, groceryList);
            listView.setAdapter(adapter);
        });

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list_history, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Configures functionality for different buttons and interactions within the grocery list history fragment.
     * - Enables long-click interactions on the list view.
     * - Displays the name of the clicked item in a toast.
     * - Removes an item when its row is long-pressed in the list view.
     * - Navigates back to the grocery list by clicking the 'Go Back' button.
     */
    private void addButtons() {

        listView.setLongClickable(true);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Show name of the clicked item
            GroupGrocery grocery = completedGroceryLiveData.getValue().get(position);
            ToastUtil.makeToast(grocery.getName(), context);
        });

        // Remove an item when its row is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItem(i);
                return false;
            }
        });

        goBack.setOnClickListener(view -> {
            FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
            callFragment(fragmentGroceryListController);
        });

    }

    /**
     * Replaces the current fragment in the main content view with the specified fragment.
     *
     * @param fragment The Fragment instance to be displayed by replacing the current fragment.
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    /**
     * Retrieves and initializes specific UI elements from the fragment's layout for managing grocery list history.
     * This method finds and assigns the necessary views, such as a 'Go Back' button and a list view,
     * required for managing and displaying the history of grocery items in the fragment.
     */
    private void loadScreenElements() {

        goBack = fragmentView.findViewById(R.id.goBackGroceryHistory);
        listView = fragmentView.findViewById(R.id.groceryListHistory);

    }

}