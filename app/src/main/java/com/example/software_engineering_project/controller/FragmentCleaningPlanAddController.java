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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Date;

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
    private static java.sql.Date startDateSql, endDateSql;
    private static int intervalSelection;
    private View fragmentView;
    private ImageView saveCleaningPlan;



    public static void handleSaveClicked() {
        checkInputsAndSave();
    }

    private static boolean checkInputsAndSave() {

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
            CleaningTemplate cleaningTemplate = new CleaningTemplate(nameString, descriptionString,
                    startDateSql, endDateSql, intervalSelection);

            //TODO create Cleanings based on selected dates

            CleaningTemplateRepository cleaningTemplateRepository = new CleaningTemplateRepository();
            cleaningTemplateRepository.createCleaningTemplate(cleaningTemplate, context);

            resetPickers();

            return true;
        }
    }

    private static void resetPickers(){
        name.setText("");
        description.setText("");
        datePickerCleaningPlan.setTag(null);
        //TODO Translation
        datePickerCleaningPlan.setText("Select Date Range");
        spinner.setSelection(0);
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

        String text = String.valueOf(spinner.getSelectedItem());
        AdapterSpinnerList.setText(text);
        if(position == 0){
            intervalSelection = 7;
        } else if(position == 1) {
            intervalSelection = 14;
        } else if(position == 2){
            intervalSelection = 28;
        } else if(position == 3){
            intervalSelection = 28 * 2;
        } else if(position == 3){
            intervalSelection = 28 * 6;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addButtons() {

        datePickerCleaningPlan.setOnClickListener(v -> showDatePickerDialog());

    }

    private java.sql.Date convertLongToSqlDate(Long date){
        Date utilDate = new Date(date);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    private void implementSpinner() {

        //TODO Translations

        //if changed, adoption in Listener required
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Weekly");
        paths.add("Bi-Weekly");
        paths.add("Monthly");
        paths.add("Bi-Monthly");
        paths.add("Half-Yearly");

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

    private void saveDates(Long startDate, Long endDate) {
        startDateSql = convertLongToSqlDate(startDate);
        endDateSql = convertLongToSqlDate(endDate);
    }

    private void showDatePickerDialog() {

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("Select Date").build();
        materialDatePicker.show(requireActivity().getSupportFragmentManager(), "TAG");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;
            saveDates(startDate, endDate);
            CharSequence charSequence = materialDatePicker.getHeaderText();
            datePickerCleaningPlan.setText(charSequence);

        });

    }

}