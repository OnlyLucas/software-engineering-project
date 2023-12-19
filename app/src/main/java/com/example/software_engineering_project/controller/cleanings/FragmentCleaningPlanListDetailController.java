package com.example.software_engineering_project.controller.cleanings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterCleaningPlanListDetailView;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.viewmodel.CleaningRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListDetailController #newInstance} factory method to
 * create an instance of this fragment.
 *
 * Fragment for displaying details of a cleaning plan, including the list of upcoming cleanings.
 */
public class FragmentCleaningPlanListDetailController extends Fragment {

    private static ArrayAdapter<Cleaning> adapter;
    private static CleaningRepository cleaningRepository;
    private static Context context;
    private static ListView listView;
    private static LiveData<List<Cleaning>> uncompletedCleaningsLiveData;
    private CleaningTemplate cleaningTemplate;
    private TextView description;
    private View fragmentView;

    /**
     * Default constructor for the FragmentCleaningPlanListDetailController.
     */
    public FragmentCleaningPlanListDetailController(){

        //default constructor

    }

    /**
     * Constructor for the FragmentCleaningPlanListDetailController that accepts a CleaningTemplate.
     *
     * @param cleaningTemplate The cleaning template for which to display details.
     */
    public FragmentCleaningPlanListDetailController(CleaningTemplate cleaningTemplate){

        this.cleaningTemplate = cleaningTemplate;

    }

    public static void removeItem(int position) {
        Cleaning cleaning = uncompletedCleaningsLiveData.getValue().get(position);
        cleaningRepository.deleteCleaning(cleaning, context);
    }

    public static void uncheckItem(int position) {
        Cleaning cleaning = uncompletedCleaningsLiveData.getValue().get(position);
        cleaning.setCompleted();
        cleaningRepository.updateCleaning(cleaning, context);
    }

    /**
     * Called to create the view for this fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list_detail, container, false);
        context = requireActivity();
        loadScreenElements();
        cleaningRepository = new CleaningRepository();

        uncompletedCleaningsLiveData = cleaningRepository.getUncompletedCleanings(cleaningTemplate.getId(), context);
        uncompletedCleaningsLiveData.observe(getViewLifecycleOwner(), cleaningList -> {
            adapter = new AdapterCleaningPlanListDetailView(context, cleaningList);
            listView.setAdapter(adapter);
        });

        description.setText(cleaningTemplate.getName() + ": " + cleaningTemplate.getDescription());

        return fragmentView;

    }

    private void loadScreenElements() {

        description = fragmentView.findViewById(R.id.descriptionCleaningPlanListDetail);
        listView = fragmentView.findViewById(R.id.cleaningPlanListDetail);

    }

}