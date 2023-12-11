package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.CleaningPlanListDetailViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListDetailController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanListDetailController extends Fragment {

    static ListView listView;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    static Context context;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list_detail, container, false);
        loadScreenElements();
        context = requireActivity();

        adapter = new CleaningPlanListDetailViewAdapter(context, items);
        items.clear();
        items.add("January");

        listView.setAdapter(adapter);

        return fragmentView;
    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.cleaningPlanListDetail);

    }
}