package com.example.software_engineering_project.controller.cleanings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterCleaningPlanListView;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.repository.CleaningRepository;
import com.example.software_engineering_project.repository.CleaningTemplateRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * Fragment controller for managing a list of cleaning plans.
 * This fragment allows users to view a list of existing cleaning plans,
 * select a specific cleaning plan to view its details, and delete cleaning plans.
 */
public class FragmentCleaningPlanListController extends Fragment {

    private static ArrayAdapter<CleaningTemplate> adapter;
    private static CleaningTemplateRepository cleaningTemplateRepository;
    private static CleaningRepository cleaningRepository;
    private static Context context;
    private static ListView listView;
    private static LiveData<List<CleaningTemplate>> currentCleaningTemplatesLiveData;
    private View fragmentView;


    /**
     * Removes a specific item from the list of cleaning templates and deletes it from the repository.
     *
     * @param i The index of the item to be removed from the list of cleaning templates.
     */
    public static void removeItem(int i) {

        CleaningTemplate cleaningTemplate = currentCleaningTemplatesLiveData.getValue().get(i);
        cleaningTemplateRepository.deleteCleaningTemplate(cleaningTemplate, context);

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * Initializes necessary repositories, observes LiveData, and sets up the list view with an adapter.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     *                           This value may be null.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = requireActivity();

        cleaningTemplateRepository = new CleaningTemplateRepository(context);
        cleaningRepository = new CleaningRepository();

        currentCleaningTemplatesLiveData = cleaningTemplateRepository.getCurrentCleaningTemplates();

        currentCleaningTemplatesLiveData.observe(getViewLifecycleOwner(), currentCleaningTemplates -> {
            adapter = new AdapterCleaningPlanListView(getActivity(), currentCleaningTemplates, cleaningRepository);
            listView.setAdapter(adapter);
        });

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list, container, false);
        loadScreenElements();
        addButtons();

        return fragmentView;

    }

    /**
     * Sets up functionality for handling item clicks in the ListView within the fragment.
     * Opens a detailed view of a clicked CleaningTemplate from the ListView.
     */
    private void addButtons() {

        listView.setOnItemClickListener((parent, view, position, id) -> {
            CleaningTemplate clicked = currentCleaningTemplatesLiveData.getValue().get(position);
            FragmentCleaningPlanListDetailController fragmentCleaningPlanListDetailController = new FragmentCleaningPlanListDetailController(clicked);
            callFragment(fragmentCleaningPlanListDetailController);
            FragmentCleaningPlanController.goBackCleaningPlan.setVisibility(View.VISIBLE);
        });

    }

    private void callFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragmentCleaningPlan, fragment);
        transaction.commit();

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.cleaningPlanList);

    }

}