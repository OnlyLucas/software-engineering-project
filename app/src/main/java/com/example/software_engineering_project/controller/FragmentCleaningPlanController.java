package com.example.software_engineering_project.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanController extends Fragment {


    @SuppressLint("StaticFieldLeak")
    static View fragmentView;
    ImageView addCleaningPlan;
    ImageView goBackCleaningPlan;
    ImageView saveCleaningPlan;

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
            goBackCleaningPlan.setVisibility(View.VISIBLE);
            saveCleaningPlan.setVisibility(View.VISIBLE);
            addCleaningPlan.setVisibility(View.INVISIBLE);
        });
        FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
        goBackCleaningPlan = fragmentView.findViewById(R.id.goBackCleaningPlan);
        goBackCleaningPlan.setOnClickListener(v -> {
            callFragment(fragmentCleaningPlanListController);
            goBackCleaningPlan.setVisibility(View.INVISIBLE);
            saveCleaningPlan.setVisibility(View.INVISIBLE);
            addCleaningPlan.setVisibility(View.VISIBLE);
        });


        saveCleaningPlan = fragmentView.findViewById(R.id.saveCleaningPlan);
    }


    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();

    }
}