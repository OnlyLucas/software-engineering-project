package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.budget.FragmentBudgetAddExpenseScreenController;
import com.example.software_engineering_project.entity.User;

import java.util.List;

/**
 * Adapter for displaying a list of users in the context of adding a new expense.
 * This adapter is designed to work with the AdapterBudgetNewExpense layout.
 *
 * The adapter provides functionality to display a checkbox for each user, allowing users to be selected or deselected.
 * Additionally, it includes event listeners to handle changes in the checkbox state and update the user list accordingly.
 */
public class AdapterBudgetNewExpense extends ArrayAdapter<User> {

    private CheckBox checkBoxNewExpenseParticipants;
    private Context context;
    private List<User> list;


    /**
     * Adapter for managing a list of users for the budget new expense feature.
     *
     * @param context The context of the calling activity or application.
     * @param items   The list of users to be displayed in the adapter.
     */
    public AdapterBudgetNewExpense(Context context, List<User> items) {

        super(context, R.layout.adapter_budget_new_expense, items);
        this.context = context;
        list = items;

    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_new_expense, null);

            loadScreenElements(convertView);

            User user = list.get(position);
            checkBoxNewExpenseParticipants.setText(user.getFirstName());

        }

        checkBoxNewExpenseParticipants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                User user = list.get(position);
                if (!isChecked) {
                    FragmentBudgetAddExpenseScreenController.addUser(user);
                } else {
                    FragmentBudgetAddExpenseScreenController.deleteUser(user);
                }

            }

        });

        checkBoxNewExpenseParticipants.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User user = list.get(position);
                if (!checkBoxNewExpenseParticipants.isSelected()) {
                    FragmentBudgetAddExpenseScreenController.addUser(user);
                } else {
                    FragmentBudgetAddExpenseScreenController.deleteUser(user);
                }

            }

        });

        return convertView;

    }

    /**
     * Initializes the CheckBox UI element named checkBoxNewExpenseParticipants
     * by finding the corresponding view from the provided convertView.
     *
     * @param convertView The View object representing the inflated layout used in the adapter.
     */
    private void loadScreenElements(View convertView) {

        checkBoxNewExpenseParticipants = convertView.findViewById(R.id.checkBoxNewExpenseParticipants);

    }

}