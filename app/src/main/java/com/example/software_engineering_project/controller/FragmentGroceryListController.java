package com.example.software_engineering_project.controller;

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
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterGroceryListListView;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.GroceryRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroceryListController extends Fragment {

    private static ListView listView;
    private static GroceryRepository groceryRepository;
    private static LiveData<List<GroupGrocery>> uncompletedGroceryLiveData;
    private static ArrayAdapter<GroupGrocery> adapter;
    private static Context context;
    private EditText input;
    private ImageView enter;
    private View fragmentView;

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int item) {
        GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(item);
        groceryRepository.deleteGroupGrocery(grocery, context);
    }

    public static void uncheckItem(int i) {
        GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(i);
        grocery.setCompleted();
        groceryRepository.updateGroupGrocery(grocery, context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        groceryRepository = new GroceryRepository();
        uncompletedGroceryLiveData = groceryRepository.getUncompletedGroupGroceries();

        // TODO maybe wont update, as list is altered, not exchanged
        uncompletedGroceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
            adapter = new AdapterGroceryListListView(getActivity(), groceryList);
            listView.setAdapter(adapter);
        });
        context = requireActivity();
        fragmentView = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        loadScreenElements();
        addButtons();

        listView.setAdapter(adapter);

        return fragmentView;
    }

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
                // get the input
                String text = input.getText().toString();

                if (text.length() == 0) {
                    // if no input
                    ToastUtil.makeToast(getString(R.string.enter_an_item_toast), context);
                } else {
                    // if input exists, create new GroupGrocery with respective attributes
                    GroupGrocery grocery = new GroupGrocery(text);
                    // add new item to database
                    addItem(grocery);
                    // empty input field
                    input.setText(R.string.empty_input_fields);
                }

            }

        });

    }

    // function to add an item given its name.
    private void addItem(GroupGrocery item) {
        groceryRepository.insertGroupGrocery(item, context);
    }

    private void loadScreenElements() {
        listView = fragmentView.findViewById(R.id.groceryList);
        input = fragmentView.findViewById(R.id.input);
        enter = fragmentView.findViewById(R.id.enter);
    }

}