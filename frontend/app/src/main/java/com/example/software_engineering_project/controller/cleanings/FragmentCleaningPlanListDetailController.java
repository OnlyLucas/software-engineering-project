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
import com.example.software_engineering_project.repository.CleaningRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListDetailController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
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
    public FragmentCleaningPlanListDetailController() {

        //default constructor

    }

    /**
     * Constructs a FragmentCleaningPlanListDetailController with a provided CleaningTemplate object.
     *
     * @param cleaningTemplate The CleaningTemplate object used for initializing this controller.
     *                         It represents the data associated with a specific cleaning plan.
     */
    public FragmentCleaningPlanListDetailController(CleaningTemplate cleaningTemplate) {

        this.cleaningTemplate = cleaningTemplate;

    }

    /**
     * Removes a specific item (Cleaning) from the list of uncompleted cleanings and deletes it from the repository.
     *
     * @param position The index position of the item to be removed from the list of uncompleted cleanings.
     */
    public static void removeItem(int position) {

        Cleaning cleaning = uncompletedCleaningsLiveData.getValue().get(position);
        cleaningRepository.deleteCleaning(cleaning, context);

    }

    /**
     * Marks a specific item (Cleaning) from the list of uncompleted cleanings as incomplete and updates it in the repository.
     *
     * @param position The index position of the item to be marked as incomplete in the list of uncompleted cleanings.
     */
    public static void uncheckItem(int position) {

        Cleaning cleaning = uncompletedCleaningsLiveData.getValue().get(position);
        cleaning.setCompleted();
        cleaningRepository.updateCleaning(cleaning, context);

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment for displaying cleaning plan details.
     * Initializes necessary elements, observes LiveData for uncompleted cleanings, and sets up the list view with an adapter.
     * Displays the description of the cleaning plan.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     *                           This value may be null.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
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

    /**
     * Retrieves and initializes specific UI elements from the fragment's layout for displaying cleaning plan details.
     * This method finds and assigns the necessary views, such as description and list view,
     * required for displaying details of a cleaning plan in the fragment.
     */
    private void loadScreenElements() {

        description = fragmentView.findViewById(R.id.descriptionCleaningPlanListDetail);
        listView = fragmentView.findViewById(R.id.cleaningPlanListDetail);

    }

}