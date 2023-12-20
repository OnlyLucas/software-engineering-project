package com.example.software_engineering_project.controller.cleanings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.AppStateRepository;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * Fragment controller for managing cleaning plans.
 * This fragment allows users to view a list of existing cleaning plans,
 * add new cleaning plans, and navigate between different cleaning plan views.
 */
public class FragmentCleaningPlanController extends Fragment {

    static ImageView goBackCleaningPlan;
    static View fragmentView;
    private FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
    private FragmentCleaningPlanAddController fragmentCleaningPlanAddController = new FragmentCleaningPlanAddController();
    private ImageView addCleaningPlan, saveCleaningPlan;
    private Context context;


    /**
     * Called to create and return the view hierarchy associated with the fragment.
     * Initializes the UI elements, sets up controllers, and prepares the fragment view for display.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     *                           This value may be null.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan, container, false);
        FragmentCleaningPlanListController fragmentCleaningPlanListController = new FragmentCleaningPlanListController();
        loadScreenElements();
        callFragment(fragmentCleaningPlanListController);
        addButtons();

        return fragmentView;

    }

    /**
     * Configures functionality for various buttons in the fragment.
     * Associates click listeners to handle actions upon button clicks, such as adding, saving, or navigating.
     * Updates button visibility based on certain conditions.
     */
    private void addButtons() {

        Group group = AppStateRepository.getCurrentGroupLiveData().getValue();

        addCleaningPlan.setOnClickListener(v -> {
            if (group == null) {
                ToastUtil.makeToast(context.getString(R.string.join_a_group_first), context);
            } else {
                callFragment(fragmentCleaningPlanAddController);
                goBackCleaningPlan.setVisibility(View.VISIBLE);
                saveCleaningPlan.setVisibility(View.VISIBLE);
                addCleaningPlan.setVisibility(View.INVISIBLE);
            }
        });

        goBackCleaningPlan.setOnClickListener(v -> {
            callFragment(fragmentCleaningPlanListController);
            goBackCleaningPlan.setVisibility(View.INVISIBLE);
            saveCleaningPlan.setVisibility(View.INVISIBLE);
            addCleaningPlan.setVisibility(View.VISIBLE);
        });

        saveCleaningPlan.setOnClickListener(view -> FragmentCleaningPlanAddController.handleSaveClicked());

    }

    /**
     * Replaces the current fragment with a new fragment specified by the parameter.
     * Uses FragmentManager to initiate a FragmentTransaction and replaces the current fragment
     * within the layout container with the specified fragment.
     *
     * @param fragment The Fragment object to be displayed within the layout container.
     */
    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();

    }

    /**
     * Retrieves and initializes specific UI elements from the fragment's layout.
     * This method finds and assigns the necessary views, such as buttons or other UI elements,
     * required for the fragment's functionality.
     */
    private void loadScreenElements() {

        addCleaningPlan = fragmentView.findViewById(R.id.addCleaningPlan);
        goBackCleaningPlan = fragmentView.findViewById(R.id.goBackCleaningPlan);
        saveCleaningPlan = fragmentView.findViewById(R.id.saveCleaningPlan);

    }

}