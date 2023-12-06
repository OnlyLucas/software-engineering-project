package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.GroceryListListViewAdapter;
import com.example.software_engineering_project.entity.GroupGrocery;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.GroceryRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroceryListController extends Fragment {

    View fragmentView;
    static ListView listView;
    EditText input;
    ImageView enter;
    static GroceryRepository groceryRepository;
    static LiveData<List<GroupGrocery>> groceryLiveData;
    static ArrayAdapter<GroupGrocery> adapter;
    static Context context;
    static Toast t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        groceryRepository = new GroceryRepository();
        groceryLiveData = groceryRepository.getGroupGroceries();

        // TODO maybe wont update, as list is altered, not exchanged
        groceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
            adapter = new GroceryListListViewAdapter(getActivity(), groceryList);
            listView.setAdapter(adapter);
        });

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        listView = fragmentView.findViewById(R.id.groceryList);

        input = fragmentView.findViewById(R.id.input);
        enter = fragmentView.findViewById(R.id.enter);
        context = getActivity();

        listView.setLongClickable(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show name of the clicked item
                GroupGrocery grocery = groceryLiveData.getValue().get(position);
                ToastUtil.makeToast(grocery.getName(), context);
            }

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
                    ToastUtil.makeToast("Enter an item.", context);
                } else {
                    // if input exists, create new GroupGrocery with respective attributes
                    GroupGrocery grocery = new GroupGrocery(text);
                    // add new item to database
                    addItem(grocery);
                    // empty input field
                    input.setText("");
                }

            }

        });

        return fragmentView;
    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int item) {
        GroupGrocery grocery = groceryLiveData.getValue().get(item);
        groceryRepository.deleteGroupGrocery(grocery, context);

    }

    public static void uncheckItem(int i) {
        GroupGrocery grocery = groceryLiveData.getValue().get(i);
        grocery.setCompleted();
        groceryRepository.updateGroupGrocery(grocery, context);
    }

    // function to add an item given its name.
    public void addItem(GroupGrocery item) {
        groceryRepository.insertGroupGrocery(item, context);
    }


}