package com.example.software_engineering_project.controller.cleanings;

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

    static ImageView goBackCleaningPlan;
    static View fragmentView;
    private FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
    private FragmentCleaningPlanAddController fragmentCleaningPlanAddController = new FragmentCleaningPlanAddController();
    private ImageView addCleaningPlan, saveCleaningPlan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan, container, false);
        FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
        loadScreenElements();
        callFragment(fragmentCleaningPlanListController);
        addButtons();

        return fragmentView;

    }

    private void addButtons() {

        addCleaningPlan.setOnClickListener(v -> {
            callFragment(fragmentCleaningPlanAddController);
            goBackCleaningPlan.setVisibility(View.VISIBLE);
            saveCleaningPlan.setVisibility(View.VISIBLE);
            addCleaningPlan.setVisibility(View.INVISIBLE);
        });

        goBackCleaningPlan.setOnClickListener(v -> {
            callFragment(fragmentCleaningPlanListController);
            goBackCleaningPlan.setVisibility(View.INVISIBLE);
            saveCleaningPlan.setVisibility(View.INVISIBLE);
            addCleaningPlan.setVisibility(View.VISIBLE);
        });

        saveCleaningPlan.setOnClickListener(view -> FragmentCleaningPlanAddController.handleSaveClicked());

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        addCleaningPlan = fragmentView.findViewById(R.id.addCleaningPlan);
        goBackCleaningPlan = fragmentView.findViewById(R.id.goBackCleaningPlan);
        saveCleaningPlan = fragmentView.findViewById(R.id.saveCleaningPlan);

    }

}