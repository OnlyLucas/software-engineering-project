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
     * Called to create the view hierarchy associated with this fragment.
     * <p>
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     * @return The root View of the fragment's layout.
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

    /**
     * Configures and assigns click listeners to buttons in the budget fragment.
     * Determines actions to perform based on button clicks.
     * Adjusts UI elements such as buttons and headlines accordingly.
     */
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
            if (group == null) {
                ToastUtil.makeToast(context.getString(R.string.join_a_group_first), context);
            } else {
                callFragment(fragmentBudgetAddExpenseScreenController);
                replaceButtons(showBudgetDetail, goBackBudgetMain, addExpense, saveExpense);
                budgetHeadline.setText(R.string.add_new_expense);
            }
        });

        saveExpense.setOnClickListener(view -> FragmentBudgetAddExpenseScreenController.handleSaveClicked());

    }

    /**
     * Replaces the current fragment in the budget main content container with a new fragment.
     *
     * @param fragment The fragment to be replaced
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentBudgetMain, fragment);
        transaction.commit();

    }

    /**
     * Initializes and assigns view elements from the layout to their respective variables.
     * This method should be called to load UI elements.
     */
    private void loadScreenElements() {

        addExpense = fragmentView.findViewById(R.id.addExpense);
        budgetHeadline = fragmentView.findViewById(R.id.budgetHeadline);
        goBackBudgetMain = fragmentView.findViewById(R.id.goBackBudgetMain);
        saveExpense = fragmentView.findViewById(R.id.saveExpense);
        showBudgetDetail = fragmentView.findViewById(R.id.showBudgetDetail);

    }

    /**
     * Replaces the visibility and clickability of multiple ImageView buttons.
     *
     * @param view1 The first ImageView to be set as invisible and not clickable
     * @param view2 The second ImageView to be set as visible and clickable
     * @param view3 The third ImageView to be set as invisible and not clickable
     * @param view4 The fourth ImageView to be set as visible and clickable
     */
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