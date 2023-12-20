package com.example.software_engineering_project.controller.groceries;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterGroceryListListView;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.GroceryRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * <p>
 * FragmentGroceryListController displays a list of uncompleted group groceries
 * and provides functionality to add, remove, and mark items as completed.
 */
public class FragmentGroceryListController extends Fragment {

    private static ListView listView;
    private static GroceryRepository groceryRepository;
    private static LiveData<List<GroupGrocery>> uncompletedGroceryLiveData;
    private static ArrayAdapter<GroupGrocery> adapter;
    private static Context context;
    private EditText input;
    private FragmentGroceryListHistoryController fragmentGroceryListHistoryController = new FragmentGroceryListHistoryController();
    private ImageView enter, history;
    private View fragmentView;

    /**
     * Removes a specific item (GroupGrocery) from the list of uncompleted groceries and deletes it from the repository.
     *
     * @param item The index of the item to be removed from the list of uncompleted groceries.
     */
    public static void removeItem(int item) {

        GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(item);
        groceryRepository.deleteGroupGrocery(grocery, context);

    }

    /**
     * Marks a specific item (GroupGrocery) from the list of uncompleted groceries as incomplete and updates it in the repository.
     *
     * @param i The index of the item to be marked as incomplete in the list of uncompleted groceries.
     */
    public static void uncheckItem(int i) {

        GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(i);
        grocery.setCompleted();
        groceryRepository.updateGroupGrocery(grocery, context);

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment for displaying the grocery list.
     * Initializes necessary elements, observes LiveData for uncompleted group groceries, and sets up the list view with an adapter.
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
        uncompletedGroceryLiveData = groceryRepository.getUncompletedGroupGroceries();

        uncompletedGroceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
            adapter = new AdapterGroceryListListView(getActivity(), groceryList);
            listView.setAdapter(adapter);
        });

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Configures functionality for different buttons and interactions within the grocery list fragment.
     * - Sets up click listeners to display the name of the clicked item in a toast.
     * - Enables removing an item when its row is long-pressed in the list view.
     * - Handles adding new grocery items and navigating to the grocery list history.
     */
    private void addButtons() {

        listView.setLongClickable(true);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Show name of the clicked item
            GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(position);
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

        // Add an item by clicking the respective button
        enter.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group group = AppStateRepository.getCurrentGroupLiveData().getValue();
                if (group == null) {
                    ToastUtil.makeToast(context.getString(R.string.join_a_group_first), context);
                } else {
                    // get the input
                    String text = input.getText().toString();

                    if (text.length() == 0) {
                        // if no input
                        ToastUtil.makeToast(getString(R.string.enter_an_itemDot), context);
                    } else if (text.length() > 32) {
                        ToastUtil.makeToast(getString(R.string.enter_shorter_name), context);
                    } else {
                        // if input exists, create new GroupGrocery with respective attributes
                        GroupGrocery grocery = new GroupGrocery(text);
                        // add new item to database
                        addItem(grocery);
                        // empty input field
                        input.setText(R.string.empty_input_fields);
                    }
                }
            }

        });

        history.setOnClickListener(view -> {
            callFragment(fragmentGroceryListHistoryController);
        });

    }

    /**
     * Inserts a new GroupGrocery item into the repository.
     *
     * @param item The GroupGrocery item to be inserted into the repository.
     */
    private void addItem(GroupGrocery item) {
        groceryRepository.insertGroupGrocery(item, context);
    }

    /**
     * Replaces the current fragment with the specified fragment in the main content view.
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
     * Retrieves and initializes specific UI elements from the fragment's layout for managing grocery lists.
     * This method finds and assigns the necessary views, such as buttons, input fields, and a list view,
     * required for managing and displaying grocery items in the fragment.
     */
    private void loadScreenElements() {

        enter = fragmentView.findViewById(R.id.enter);
        input = fragmentView.findViewById(R.id.input);
        history = fragmentView.findViewById(R.id.historyGroceryList);
        listView = fragmentView.findViewById(R.id.groceryList);

    }

}