package com.example.software_engineering_project.controller;

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
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.CleaningTemplateRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanListController extends Fragment {

    private static ArrayAdapter<CleaningTemplate> adapter;
    private static ArrayList<String> items = new ArrayList<>();
    private static CleaningTemplateRepository cleaningTemplateRepository;
    private static Context context;
    private static ListView listView;
    private static LiveData<List<CleaningTemplate>> currentCleaningTemplates;
    private View fragmentView;


    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {

        CleaningTemplate cleaningTemplate = currentCleaningTemplates.getValue().get(i);
        cleaningTemplateRepository.deleteCleaningTemplate(cleaningTemplate, context);
        ToastUtil.makeToast(context.getString(R.string.removed) + items.get(i), context);
        items.remove(i);
        listView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cleaningTemplateRepository = new CleaningTemplateRepository();

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_list, container, false);
        loadScreenElements();
        addButtons();
        context = requireActivity();

        CleaningTemplateRepository cleaningTemplateRepository = new CleaningTemplateRepository();
        currentCleaningTemplates = cleaningTemplateRepository.getCurrentCleaningTemplates();
        currentCleaningTemplates.observe(getViewLifecycleOwner(), currentCleaningTemplates -> {
            adapter = new AdapterCleaningPlanListView(getActivity(), currentCleaningTemplates);
            listView.setAdapter(adapter);
        });

        return fragmentView;

    }

    private void addButtons() {

        listView.setOnItemClickListener((parent, view, position, id) -> {
            CleaningTemplate clicked = (CleaningTemplate) listView.getItemAtPosition(position);
            System.out.println(clicked.toString());
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