package com.example.software_engineering_project.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.software_engineering_project.R;
import com.google.android.material.datepicker.MaterialDatePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanAddController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanAddController extends Fragment {

    Button datePickerButton;
    TextView textViewShowDate;
    View fragmentView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_add, container, false);
        addButtons();
        context = getActivity();
        return fragmentView;
    }

    private void addButtons() {
        datePickerButton = fragmentView.findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        textViewShowDate = fragmentView.findViewById(R.id.textViewShowDate);
    }


    private void showDatePickerDialog() {

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date").build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String text = materialDatePicker.getHeaderText();
            textViewShowDate.setText(text);
        });

        materialDatePicker.show(getActivity().getSupportFragmentManager(), "TAG");

        /**
        DatePickerDialog datePicker = new DatePickerDialog(context);
        datePicker.show();
        int startYear;
        int startMonth;
        int startDayOfMonth;

        datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                year = view.getYear();
                month = view.getMonth();
                dayOfMonth = view.getDayOfMonth();

                String text = "" + year + " " + month + " " + dayOfMonth;
                textViewShowDate.setText(text);

            }
        });
         */
    }
}