package com.example.software_engineering_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetMainController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetMainController extends Fragment {

    View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentBudgetListController fragmentBudgetListController = new FragmentBudgetListController();
        callFragment(fragmentBudgetListController);

        fragmentView = inflater.inflate(R.layout.fragment_budget_main, container, false);

        return fragmentView;
    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentBudgetMain, fragment);
        transaction.commit();
    }
}