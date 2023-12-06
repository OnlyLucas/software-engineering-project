package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetListFirstLayer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetListController extends Fragment {

    View fragmentView;
    static ListView listView;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    static Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_list, container, false);
        loadScreenElements();
        context = requireActivity();

        adapter = new AdapterBudgetListFirstLayer(context, items);
        items.clear();
        items.add("January");
        items.add("February");
        items.add("March");
        items.add("April");
        items.add("May");
        items.add("June");
        items.add("July");
        items.add("August");

        listView.setAdapter(adapter);

        return fragmentView;
    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.budgetListFirstLayer);

    }
}