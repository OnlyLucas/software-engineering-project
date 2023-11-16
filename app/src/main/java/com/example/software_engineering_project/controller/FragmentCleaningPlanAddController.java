package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.software_engineering_project.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanAddController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanAddController extends Fragment {

    MaterialButton datePickerCleaningPlan;
    ImageView saveCleaningPlan;
    View fragmentView;
    Context context;
    String startDateString;
    Long startDateLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_add, container, false);
        addButtons();
        context = getActivity();
        return fragmentView;
    }

    private void addButtons() {
        datePickerCleaningPlan = fragmentView.findViewById(R.id.datePickerCleaningPlan);
        datePickerCleaningPlan.setOnClickListener(v -> showDatePickerDialog());

        saveCleaningPlan = FragmentCleaningPlanController.fragmentView.findViewById(R.id.saveCleaningPlan);
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
}