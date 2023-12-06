package com.example.software_engineering_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetDetailScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetDetailScreenController extends Fragment {

    View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);

        return fragmentView;

    }
}