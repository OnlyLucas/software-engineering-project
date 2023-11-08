package com.example.software_engineering_project.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.software_engineering_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CleaningPlanFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class CleaningPlanFragment extends DialogFragment {

    private Button goBackButtonCleaningPlan;
    private  Button editCleaningPlan;
    public static Fragment getInstance() {
        return new CleaningPlanFragment();
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // you can call xml element by using view.findviewbyid ..

        View view = inflater.inflate(R.layout.fragment_cleaning_plan, container, false);
        this.addButtons();
        return view;

    }

    public void addButtons(){

    }

}