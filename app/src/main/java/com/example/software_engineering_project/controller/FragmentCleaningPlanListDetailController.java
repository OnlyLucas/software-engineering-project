package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterCleaningPlanListDetailView;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.viewmodel.CleaningRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListDetailController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanListDetailController extends Fragment {

    private static ArrayAdapter<Cleaning> adapter;
    private static ArrayList<String> items = new ArrayList<>();
    private static CleaningRepository cleaningRepository;
    private static Context context;
    private static ListView listView;
    private CleaningTemplate cleaningTemplate;
    private View fragmentView;

    public FragmentCleaningPlanListDetailController(){

        //default constructor

    }

    public FragmentCleaningPlanListDetailController(CleaningTemplate cleaningTemplate){

        this.cleaningTemplate = cleaningTemplate;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list_detail, container, false);
        context = requireActivity();
        loadScreenElements();
        cleaningRepository = new CleaningRepository();

        //TODO Get next Cleanings for CleaningTemplate
        LiveData<List<Cleaning>> cleaningsLiveData = cleaningRepository.getUncompletedCleanings(cleaningTemplate.getId());

        cleaningsLiveData.observe(getViewLifecycleOwner(), cleaningList -> {
            adapter = new AdapterCleaningPlanListDetailView(context, cleaningList);
            listView.setAdapter(adapter);
        });

        return fragmentView;

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.cleaningPlanListDetail);

    }

}