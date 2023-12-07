package com.example.software_engineering_project.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.software_engineering_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetMainController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetMainController extends Fragment {

    FragmentBudgetListController fragmentBudgetListController = new FragmentBudgetListController();
    FragmentBudgetDetailScreenController fragmentBudgetDetailScreenController = new FragmentBudgetDetailScreenController();
    FragmentBudgetAddExpenseScreenController fragmentBudgetAddExpenseScreenController = new FragmentBudgetAddExpenseScreenController();
    View fragmentView;
    ImageView showBudgetDetail;
    ImageView goBackBudgetMain;
    ImageView addExpense;
    ImageView saveExpense;
    TextView budgetHeadline;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_main, container, false);
        loadScreenElements();
        callFragment(fragmentBudgetListController);
        addButtons();
        return fragmentView;
    }

    private void loadScreenElements() {

        showBudgetDetail = fragmentView.findViewById(R.id.showBudgetDetail);
        goBackBudgetMain = fragmentView.findViewById(R.id.goBackBudgetMain);
        budgetHeadline = fragmentView.findViewById(R.id.budgetHeadline);
        addExpense = fragmentView.findViewById(R.id.addExpense);
        saveExpense = fragmentView.findViewById(R.id.saveExpense);

    }


    private void addButtons() {

        showBudgetDetail.setOnClickListener(view -> {
            callFragment(fragmentBudgetDetailScreenController);
            replaceButtons(showBudgetDetail, goBackBudgetMain, saveExpense, addExpense);
            budgetHeadline.setText("Details");

        });

        goBackBudgetMain.setOnClickListener(view -> {
            callFragment(fragmentBudgetListController);
            replaceButtons(goBackBudgetMain, showBudgetDetail, saveExpense, addExpense);
            budgetHeadline.setText(getText(R.string.budget));
        });

        addExpense.setOnClickListener(view -> {
            callFragment(fragmentBudgetAddExpenseScreenController);
            replaceButtons(showBudgetDetail, goBackBudgetMain, addExpense, saveExpense);
            budgetHeadline.setText("Add new Expense");
        });

        //TODO: Implement save method to button and create button here
        saveExpense.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentBudgetAddExpenseScreenController.handleSaveClicked();
            }
        });

    }


    private void replaceButtons(ImageView view1, ImageView view2, ImageView view3, ImageView view4) {

        view1.setVisibility(View.INVISIBLE);
        view1.setClickable(false);
        view2.setVisibility(View.VISIBLE);
        view2.setClickable(true);
        view3.setVisibility(View.INVISIBLE);
        view3.setClickable(false);
        view4.setVisibility(View.VISIBLE);
        view4.setClickable(true);

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentBudgetMain, fragment);
        transaction.commit();
    }
}