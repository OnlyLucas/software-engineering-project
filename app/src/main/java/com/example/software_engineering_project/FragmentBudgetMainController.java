package com.example.software_engineering_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetMainController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetMainController extends Fragment {

    FragmentBudgetListController fragmentBudgetListController = new FragmentBudgetListController();
    FragmentBudgetDetailScreenController fragmentBudgetDetailScreenController = new FragmentBudgetDetailScreenController();
    View fragmentView;
    ImageView showBudgetDetail;
    ImageView goBackBudgetMain;
    TextView budgetHeadline;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_budget_main, container, false);
        callFragment(fragmentBudgetListController);
        addButtons();
        return fragmentView;
    }


    private void addButtons() {

        showBudgetDetail = fragmentView.findViewById(R.id.showBudgetDetail);
        showBudgetDetail.setOnClickListener(view -> {
            callFragment(fragmentBudgetDetailScreenController);
            replaceButtons(showBudgetDetail, goBackBudgetMain);
            budgetHeadline = fragmentView.findViewById(R.id.budgetHeadline);
            budgetHeadline.setText("Details");

        });

        goBackBudgetMain = fragmentView.findViewById(R.id.goBackBudgetMain);
        goBackBudgetMain.setOnClickListener(view -> {
            callFragment(fragmentBudgetListController);
            replaceButtons(goBackBudgetMain, showBudgetDetail);
            budgetHeadline = fragmentView.findViewById(R.id.budgetHeadline);
            budgetHeadline.setText(getText(R.string.budget));
        });

    }

    private void replaceButtons(ImageView view1, ImageView view2) {

        view1.setVisibility(View.INVISIBLE);
        view1.setClickable(false);
        view2.setVisibility(View.VISIBLE);
        view2.setClickable(true);

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentBudgetMain, fragment);
        transaction.commit();
    }
}