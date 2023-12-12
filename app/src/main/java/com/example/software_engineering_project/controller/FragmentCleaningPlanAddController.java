package com.example.software_engineering_project.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.AdapterSpinnerList;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.CleaningTemplateRepository;
import com.example.software_engineering_project.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanAddController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCleaningPlanAddController extends Fragment implements AdapterView.OnItemSelectedListener {

    static Spinner spinner;
    private static Context context;
    private static EditText description, name;
    private static MaterialButton datePickerCleaningPlan;
    private ImageView saveCleaningPlan;
    private Long startDateLong;
    private String startDateString, test;
    private View fragmentView;


    public static void handleSaveClicked() {    //TODO Braucht es diese Methode wirklich oder können wir direkt
        checkInputs();                          //die checkInputs aufrufen?
    }

    private static boolean checkInputs() {
        // get the inputs
        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();
        if (nameString.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_name_for_cleaning_plan), context);
            return false;
        } else if (descriptionString.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_name_for_cleaning_plan), context);
            return false;
        } else {
            String interval = spinner.getSelectedItem().toString();
            int start = datePickerCleaningPlan.getSelectionStart();
            int end = datePickerCleaningPlan.getSelectionEnd();

            //TODO in Konstruktor von CleaningTemplate auslagern
            CleaningTemplate cleaningTemplate = new CleaningTemplate();
            cleaningTemplate.setName(nameString);
            cleaningTemplate.setDescription(descriptionString);
            cleaningTemplate.setCreatedByUser(UserViewModel.getCurrentAppUser().getValue());
            cleaningTemplate.setGroup(UserViewModel.getCurrentGroup().getValue());

            //TODO Dates/ints verstehen und richtig converten
            //cleaningTemplate.setStartDate((java.sql.Date) new Date(start));
            //cleaningTemplate.setEndDate((java.sql.Date) new Date(end));
            cleaningTemplate.setStartDate(new java.sql.Date(2023 - 12 - 12));
            cleaningTemplate.setEndDate(new java.sql.Date(2024 - 12 - 15));

            //TODO Convert selection to int and get DB keyword interval away
            //cleaningTemplate.setInterval(7);

            //TODO create Cleanings based on selected dates

            CleaningTemplateRepository cleaningTemplateRepository = new CleaningTemplateRepository();
            cleaningTemplateRepository.createCleaningTemplate(cleaningTemplate, context);
            return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_add, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        implementSpinner();
        return fragmentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        AdapterSpinnerList.setText(String.valueOf(spinner.getSelectedItem()));
        //TODO daten fürs Backend abgreifen

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addButtons() {

        datePickerCleaningPlan.setOnClickListener(v -> showDatePickerDialog());

    }

    private void implementSpinner() {

        //TODO überdenken bzw. ändern
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Weekly");
        paths.add("Bi-Weekly");
        paths.add("Monthly");

        ArrayAdapter<String> adapter = new AdapterSpinnerList(context, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    private void loadScreenElements() {

        datePickerCleaningPlan = fragmentView.findViewById(R.id.datePickerCleaningPlan);
        saveCleaningPlan = FragmentCleaningPlanController.fragmentView.findViewById(R.id.saveCleaningPlan);
        spinner = fragmentView.findViewById(R.id.chooseInterval);
        name = fragmentView.findViewById(R.id.enterNameAddCleaningPlan);
        description = fragmentView.findViewById(R.id.enterDescriptionCleaningPlan);

    }

    private void saveSelectedDate(String string, Long l) {

        startDateString = string;
        startDateLong = l;

    }

    private void showDatePickerDialog() {

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText(R.string.select_date).build();
        materialDatePicker.show(requireActivity().getSupportFragmentManager(), "TAG");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;
            CharSequence charSequence = materialDatePicker.getHeaderText();
            datePickerCleaningPlan.setText(charSequence);
            saveSelectedDate(String.valueOf(startDate), startDate);

        });

    }

}