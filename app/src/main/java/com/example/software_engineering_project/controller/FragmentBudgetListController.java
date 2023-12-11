package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterBudgetListFirstLayer;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.viewmodel.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudgetListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudgetListController extends Fragment {

    private static AdapterBudgetListFirstLayer adapter;
    private static Context context;
    private static ArrayList<String> items = new ArrayList<>();
    private static ListView listView;
    private static PaymentRepository paymentRepository;
    private LiveData<List<Payment>> currentPayments;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        paymentRepository = new PaymentRepository();
        currentPayments = paymentRepository.getCurrentPayments();

        fragmentView = inflater.inflate(R.layout.fragment_budget_list, container, false);
        loadScreenElements();
        context = requireActivity();

        //TODO richtige Liste
        items.clear();
        items.add("January");
        items.add("February");
        items.add("March");
        items.add("April");
        items.add("May");
        items.add("June");
        items.add("July");
        items.add("August");

        currentPayments.observe(getViewLifecycleOwner(), currentPayments -> {
            adapter = new AdapterBudgetListFirstLayer(context, items, paymentRepository.getCurrentPayments());
            listView.setAdapter(adapter);
        });

        return fragmentView;

    }

    private void loadScreenElements() {

        listView = fragmentView.findViewById(R.id.budgetListFirstLayer);

    }
}