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

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.GroupGrocery;

public class FragmentGroceryListHistoryController extends Fragment {

    private static ListView listView;
    //private static GroceryRepository groceryRepository;
    //private static LiveData<List<GroupGrocery>> uncompletedGroceryLiveData;
    private static ArrayAdapter<GroupGrocery> adapter;
    private static Context context;
    private ImageView goBack;
    private View fragmentView;

    // function to remove an item given its index in the grocery list.
    //public static void removeItem(int item) {
    //    GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(item);
    //    groceryRepository.deleteGroupGrocery(grocery, context);
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = requireActivity();
        //groceryRepository = new GroceryRepository();
        //uncompletedGroceryLiveData = groceryRepository.getUncompletedGroupGroceries();

        //uncompletedGroceryLiveData.observe(getViewLifecycleOwner(), groceryList -> {
        //    adapter = new AdapterGroceryListHistory(context, groceryList);
        //    listView.setAdapter(adapter);
        //});

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list_history, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    private void addButtons() {

        listView.setLongClickable(true);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Show name of the clicked item
            //GroupGrocery grocery = uncompletedGroceryLiveData.getValue().get(position);
            //ToastUtil.makeToast(grocery.getName(), context);
        });

        // Remove an item when its row is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //removeItem(i);
                return false;
            }
        });

        goBack.setOnClickListener(view -> {
            FragmentGroceryListController fragmentGroceryListController = new FragmentGroceryListController();
            callFragment(fragmentGroceryListController);
        });

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentMainScreen, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        goBack = fragmentView.findViewById(R.id.goBackGroceryHistory);
        listView = fragmentView.findViewById(R.id.groceryListHistory);

    }

}