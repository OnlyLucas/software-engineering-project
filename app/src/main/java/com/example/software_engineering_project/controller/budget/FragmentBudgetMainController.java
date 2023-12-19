package com.example.software_engineering_project.controller.budget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.AppStateRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetMainController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetMainController extends Fragment {

    private FragmentBudgetAddExpenseScreenController fragmentBudgetAddExpenseScreenController = new FragmentBudgetAddExpenseScreenController();
    private FragmentBudgetDetailScreenController fragmentBudgetDetailScreenController = new FragmentBudgetDetailScreenController();
    private FragmentBudgetListController fragmentBudgetListController = new FragmentBudgetListController();
    private ImageView addExpense, goBackBudgetMain, saveExpense, showBudgetDetail;
    private TextView budgetHeadline;
    private View fragmentView;
    private Context context;


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI will be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The root view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        fragmentView = inflater.inflate(R.layout.fragment_budget_main, container, false);
        loadScreenElements();
        callFragment(fragmentBudgetListController);
        addButtons();
        return fragmentView;

    }

    private void addButtons() {
        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();

        showBudgetDetail.setOnClickListener(view -> {
            callFragment(fragmentBudgetDetailScreenController);
            replaceButtons(showBudgetDetail, goBackBudgetMain, saveExpense, addExpense);
            budgetHeadline.setText(R.string.details);

        });

        goBackBudgetMain.setOnClickListener(view -> {
            callFragment(fragmentBudgetListController);
            replaceButtons(goBackBudgetMain, showBudgetDetail, saveExpense, addExpense);
            budgetHeadline.setText(getText(R.string.budget));
        });

        addExpense.setOnClickListener(view -> {
            if(group == null) {
                ToastUtil.makeToast(context.getString(R.string.join_a_group_first), context);
            } else {
                callFragment(fragmentBudgetAddExpenseScreenController);
                replaceButtons(showBudgetDetail, goBackBudgetMain, addExpense, saveExpense);
                budgetHeadline.setText(R.string.add_new_expense);
            }
        });

        saveExpense.setOnClickListener(view -> FragmentBudgetAddExpenseScreenController.handleSaveClicked());

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentBudgetMain, fragment);
        transaction.commit();
    }

    private void loadScreenElements() {

        addExpense = fragmentView.findViewById(R.id.addExpense);
        budgetHeadline = fragmentView.findViewById(R.id.budgetHeadline);
        goBackBudgetMain = fragmentView.findViewById(R.id.goBackBudgetMain);
        saveExpense = fragmentView.findViewById(R.id.saveExpense);
        showBudgetDetail = fragmentView.findViewById(R.id.showBudgetDetail);

    }

    //Todo rename params
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

}