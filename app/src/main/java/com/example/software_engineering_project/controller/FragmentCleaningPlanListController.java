package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.CleaningPlanListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanListController extends Fragment {

    View fragmentView;
    static ListView listView;
    static ArrayAdapter<String> adapter;
    static ArrayList<String> items = new ArrayList<>();
    static Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new CleaningPlanListViewAdapter(getActivity(), items);
        items.clear();
        items.add("Kehren");
        items.add("Wischen");
        items.add("Bad");

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list, container, false);
        listView = fragmentView.findViewById(R.id.cleaningPlanList);
        listView.setAdapter(adapter);

        context = getActivity();

        return fragmentView;
    }

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

    // function to make a Toast given a string
    static Toast t;

    private static void makeToast(String s) {

        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();

    }
}