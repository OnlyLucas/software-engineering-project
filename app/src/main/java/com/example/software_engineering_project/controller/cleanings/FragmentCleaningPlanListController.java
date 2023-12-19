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
import com.example.software_engineering_project.viewmodel.CleaningRepository;
import com.example.software_engineering_project.viewmodel.CleaningTemplateRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListController #newInstance} factory method to
 * create an instance of this fragment.
 *
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
     * Removes a cleaning plan at the specified position from the list.
     *
     * @param i The position of the cleaning plan to be removed in the list.
     * @throws IndexOutOfBoundsException If the specified position is out of the range of the cleaning template list.
     */
    public static void removeItem(int i) {
        CleaningTemplate cleaningTemplate = currentCleaningTemplatesLiveData.getValue().get(i);
        cleaningTemplateRepository.deleteCleaningTemplate(cleaningTemplate, context);
    }

    /**
     * Called when the fragment is created.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The root view of the fragment.
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