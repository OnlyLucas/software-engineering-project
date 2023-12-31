package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.CleaningPlanListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanController extends Fragment {

    View fragmentView;
    ImageView addCleaningPlan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
        callFragment(fragmentCleaningPlanListController);
        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan, container, false);
        //context = getActivity();
        addButtons();
        return fragmentView;
    }

    private void addButtons() {
        FragmentCleaningPlanAddController fragmentCleaningPlanAddController = new FragmentCleaningPlanAddController();
        addCleaningPlan = fragmentView.findViewById(R.id.addCleaningPlan);
        addCleaningPlan.setOnClickListener(v -> {
            callFragment(fragmentCleaningPlanAddController);
        });
    }


    private void callFragment(Fragment fragment){

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();

    }
}