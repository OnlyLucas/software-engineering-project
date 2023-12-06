package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.FragmentCleaningPlanListDetailController;
import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.CleaningPlanListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanListController extends Fragment {

    static ListView listView;
    static ArrayAdapter<String> adapter;
    static ArrayList<String> items = new ArrayList<>();
    static Context context;
    // function to make a Toast given a string
    static Toast t;
    View fragmentView;
    ImageView goBackCleaningPlan;

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {

        makeToast("Removed: " + items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);

    }

    public static void uncheckItem(int i) {
        makeToast("Unchecked: " + items.get(i));
    }

    // function to add an item given its name.
    public static void addItem(String item) {

        items.add(item);
        listView.setAdapter(adapter);

    }

    private static void makeToast(String s) {

        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list, container, false);
        loadScreenElements();
        addButtons();
        context = requireActivity();

        adapter = new CleaningPlanListViewAdapter(getActivity(), items);
        items.clear();
        items.add("Kehren");
        items.add("Wischen");
        items.add("Bad");

        listView.setAdapter(adapter);

        return fragmentView;
    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.cleaningPlanList);

    }

    private void addButtons() {

        FragmentCleaningPlanListDetailController fragmentCleaningPlanListDetailController = new FragmentCleaningPlanListDetailController();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            callFragment(fragmentCleaningPlanListDetailController);
            FragmentCleaningPlanController.goBackCleaningPlan.setVisibility(View.VISIBLE);

        });
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();
    }


}