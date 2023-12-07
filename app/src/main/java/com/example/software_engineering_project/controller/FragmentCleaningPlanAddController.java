package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.SpinnerListAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanAddController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanAddController extends Fragment implements AdapterView.OnItemSelectedListener{

    public static Spinner spinner;
    private MaterialButton datePickerCleaningPlan;
    private ImageView saveCleaningPlan;
    private View fragmentView;
    private Context context;
    private String startDateString, test;
    private Long startDateLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_add, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        implementSpinner();
        return fragmentView;
    }

    private void loadScreenElements() {

        datePickerCleaningPlan = fragmentView.findViewById(R.id.datePickerCleaningPlan);
        saveCleaningPlan = FragmentCleaningPlanController.fragmentView.findViewById(R.id.saveCleaningPlan);
        spinner = fragmentView.findViewById(R.id.chooseInterval);

    }

    private void addButtons() {

        datePickerCleaningPlan.setOnClickListener(v -> showDatePickerDialog());
        saveCleaningPlan.setOnClickListener(v -> setLog());

    }

    private void showDatePickerDialog() {

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("Select Date").build();
        materialDatePicker.show(requireActivity().getSupportFragmentManager(), "TAG");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;
            CharSequence charSequence = materialDatePicker.getHeaderText();
            datePickerCleaningPlan.setText(charSequence);
            saveSelectedDate(String.valueOf(startDate), startDate);

        });

    }

    private void saveSelectedDate(String string, Long l) {
        startDateString = string;
        startDateLong = l;
    }

    private void setLog() {
        Date date = new Date(startDateLong);

        /*
         * @SuppressLint("SimpleDateFormat")
         *         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         *         String string1 = formatter.format(date);
         */

        Log.e("DatePicker", String.valueOf(date));
    }

    private void implementSpinner() {


        //final String[] paths = {"Weekly", "Bi-Weekly", "Monthly"};
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Weekly");
        paths.add("Bi-Weekly");
        paths.add("Monthly");

        ArrayAdapter<String> adapter = new SpinnerListAdapter(context, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerListAdapter.setText(String.valueOf(spinner.getSelectedItem()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}