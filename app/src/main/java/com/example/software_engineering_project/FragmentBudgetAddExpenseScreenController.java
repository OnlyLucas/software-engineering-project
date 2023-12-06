package com.example.software_engineering_project;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.software_engineering_project.adapter.AdapterBudgetNewExpense;
import com.example.software_engineering_project.adapter.GroceryListListViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetAddExpenseScreenController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetAddExpenseScreenController extends Fragment {

    View fragmentView;
    static ListView listView;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_budget_add_expense_screen, container, false);
        context = requireActivity();
        laodScreenElements();
        adapter = new AdapterBudgetNewExpense(requireActivity(),items);
        items.clear();
        items.add("Meike");
        items.add("Lucas");
        items.add("Jonas");

        listView.setAdapter(adapter);


        return fragmentView;
    }

    private void laodScreenElements() {

        listView = fragmentView.findViewById(R.id.enterNewExpenseInvolvedPersons);

    }
}