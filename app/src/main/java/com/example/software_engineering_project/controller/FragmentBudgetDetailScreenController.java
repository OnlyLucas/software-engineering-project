package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailGet;
import com.example.software_engineering_project.adapter.AdapterBudgetDetailOwe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetDetailScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetDetailScreenController extends Fragment {

    private static ArrayList<String> itemsGet = new ArrayList<>();
    private static ArrayList<String> itemsOwe = new ArrayList<>();
    private AdapterBudgetDetailGet adapterBudgetDetailGet;
    private AdapterBudgetDetailOwe adapterBudgetDetailOwe;
    private Context context;
    private View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_detail_screen, container, false);
        context = requireActivity();

        adapterBudgetDetailGet = new AdapterBudgetDetailGet(context, itemsGet);
        adapterBudgetDetailOwe = new AdapterBudgetDetailOwe(context, itemsOwe);

        return fragmentView;

    }

}