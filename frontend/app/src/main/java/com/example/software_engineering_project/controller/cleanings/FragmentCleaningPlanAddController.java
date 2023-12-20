package com.example.software_engineering_project.controller.cleanings;

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
import com.example.software_engineering_project.repository.CleaningTemplateRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCleaningPlanAddController #newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * Fragment controller for adding cleaning plans.
 * This fragment allows users to add new cleaning plans, providing details such as name, description,
 * date range, and cleaning interval.
 */
public class FragmentCleaningPlanAddController extends Fragment implements AdapterView.OnItemSelectedListener {

    static Spinner spinner;
    private static Context context;
    private static EditText description, name;
    private static MaterialButton datePickerCleaningPlan;
    private static java.sql.Date startDateSql, endDateSql;
    private static final Integer defaultWeeklyInterval = 7;
    private static int intervalSelection = defaultWeeklyInterval;
    private View fragmentView;
    private ImageView saveCleaningPlan;


    /**
     * Handles the save button click event.
     * Calls the checkInputsAndSave method to validate inputs and save the cleaning plan.
     */
    public static void handleSaveClicked() {
        checkInputs();
    }

    /**
     * Checks the inputs for creating a cleaning plan and performs validations.
     *
     * @return Returns true if all inputs are valid and a cleaning plan can be created.
     *         Returns false if any of the validations fail or inputs are missing.
     */
    private static boolean checkInputs() {

        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();

        if (nameString.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_name_for_cleaning_plan), context);
            return false;
        } else if (nameString.length() > 15) {
            ToastUtil.makeToast(context.getString(R.string.enter_shorter_name), context);
            return false;
        } else if (descriptionString.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_description_for_cleaning_plan), context);
            return false;
        } else if (nameString.length() > 30) {
            ToastUtil.makeToast(context.getString(R.string.enter_shorter_description), context);
            return false;
        } else if (startDateSql == null || endDateSql == null) {
            // in case user deletes date input
            ToastUtil.makeToast(context.getString(R.string.please_select_valid_date_range), context);
            return false;
        } else {
            CleaningTemplate cleaningTemplate = new CleaningTemplate(nameString, descriptionString,
                    startDateSql, endDateSql, intervalSelection);

            CleaningTemplateRepository cleaningTemplateRepository = new CleaningTemplateRepository(context);
            cleaningTemplateRepository.createCleaningTemplate(cleaningTemplate, context);

            resetPickers();

            return true;
        }
    }

    /**
     * Resets various input elements such as text fields, date pickers, and spinners to their default state.
     * Clears the entered text in the name and description fields, resets date picker tags and text,
     * and sets the spinner selection to its default position.
     */
    private static void resetPickers() {

        name.setText("");
        description.setText("");
        datePickerCleaningPlan.setTag(null);
        datePickerCleaningPlan.setText(context.getString(R.string.select_date_range));
        spinner.setSelection(0);

    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     *                           This value may be null.
     * @return The View for the fragment's UI, or null if the fragment does not provide a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_cleaning_plan_add, container, false);
        context = requireActivity();
        loadScreenElements();
        addButtons();
        implementSpinner();
        return fragmentView;

    }

    /**
     * Callback method triggered when an item in the spinner is selected by the user.
     * This method is invoked when an item in the spinner is chosen, triggering specific actions based on the selection.
     *
     * @param parent   The AdapterView (Spinner) where the selection occurred.
     * @param view     The View within the AdapterView that was selected by the user.
     * @param position The position of the selected item within the adapter's data set.
     * @param id       The row id of the item that has been selected.
     *                 This parameter is the same as the position if the adapter is using a simple cursor.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = String.valueOf(spinner.getSelectedItem());
        AdapterSpinnerList.setText(text);
        if (position == 0) {
            intervalSelection = defaultWeeklyInterval;
        } else if (position == 1) {
            intervalSelection = 14;
        } else if (position == 2) {
            intervalSelection = 28;
        } else if (position == 3) {
            intervalSelection = 28 * 2;
        } else if (position == 4) {
            intervalSelection = 28 * 6;
        }

    }

    /**
     * Callback method triggered when no item in the spinner is selected.
     * This method is invoked when the selection disappears from the view or when there is no selection present.
     *
     * @param parent The AdapterView (Spinner) where the selection should have been made,
     *               but no selection is currently present.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Adds functionality to buttons or views within the fragment.
     * In this case, associates an OnClickListener with the datePickerCleaningPlan view
     * to trigger the display of a date picker dialog when clicked.
     */
    private void addButtons() {

        datePickerCleaningPlan.setOnClickListener(v -> showDatePickerDialog());

    }

    /**
     * Converts a Long value representing milliseconds since January 1, 1970, into a java.sql.Date object.
     *
     * @param date The Long value representing milliseconds since January 1, 1970 (Epoch time).
     * @return A java.sql.Date object representing the date converted from the given Long value.
     */
    private java.sql.Date convertLongToSqlDate(Long date) {

        Date utilDate = new Date(date);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;

    }

    /**
     * Sets up and populates the spinner (dropdown menu) with interval options and attaches an
     * OnItemSelectedListener to handle user selection.
     * The spinner displays interval options such as weekly, bi-weekly, monthly, bi-monthly, and half-yearly.
     */
    private void implementSpinner() {

        ArrayList<String> paths = new ArrayList<>();
        paths.add(context.getString(R.string.weekly));
        paths.add(context.getString(R.string.bi_weekly));
        paths.add(context.getString(R.string.monthly));
        paths.add(context.getString(R.string.bi_monthly));
        paths.add(context.getString(R.string.half_yearly));

        ArrayAdapter<String> adapter = new AdapterSpinnerList(context, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    /**
     * Retrieves and initializes various UI elements from the fragment's layout.
     * This method locates and assigns necessary views such as date pickers, buttons, spinners,
     * text fields, etc., required for the functionality within the fragment.
     */
    private void loadScreenElements() {

        datePickerCleaningPlan = fragmentView.findViewById(R.id.datePickerCleaningPlan);
        saveCleaningPlan = FragmentCleaningPlanController.fragmentView.findViewById(R.id.saveCleaningPlan);
        spinner = fragmentView.findViewById(R.id.chooseInterval);
        name = fragmentView.findViewById(R.id.enterNameAddCleaningPlan);
        description = fragmentView.findViewById(R.id.enterDescriptionCleaningPlan);

    }

    /**
     * Saves and processes the start and end dates for the cleaning plan.
     *
     * @param startDate The start date represented as a Long value (milliseconds since January 1, 1970).
     * @param endDate   The end date represented as a Long value (milliseconds since January 1, 1970).
     */
    private void saveDates(Long startDate, Long endDate) {

        startDateSql = convertLongToSqlDate(startDate);
        endDateSql = convertLongToSqlDate(endDate);

        // get current date
        Calendar currentDate = Calendar.getInstance();
        Date today = currentDate.getTime();

        // Convert java.sql.Date to java.util.Date
        java.util.Date startDateUtil = new java.util.Date(startDateSql.getTime());
        java.util.Date endDateUtil = new java.util.Date(endDateSql.getTime());

        if (startDateUtil.before(today) || endDateUtil.before(today)) {
            ToastUtil.makeToast(context.getString(R.string.no_past_date_allowed), context);
            //TODO not shown
            datePickerCleaningPlan.setTag(null);
            datePickerCleaningPlan.setText(context.getString(R.string.select_date_range));
            startDateSql = null;
            endDateSql = null;
        }
    }

    /**
     * Displays a MaterialDatePicker dialog for selecting a date range and handles the selection.
     * Validates the selected date range and triggers the processing of start and end dates using the saveDates() method.
     * Updates the text of the datePickerCleaningPlan view with the selected date range.
     */
    private void showDatePickerDialog() {

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        calendarConstraints.setValidator(DateValidatorPointForward.now());
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(R.string.select_date_range)
                .setCalendarConstraints(calendarConstraints.build())
                .build();
        materialDatePicker.show(requireActivity().getSupportFragmentManager(), "material_date_picker");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;
            saveDates(startDate, endDate);
            CharSequence charSequence = materialDatePicker.getHeaderText();
            datePickerCleaningPlan.setText(charSequence);

        });
    }
}