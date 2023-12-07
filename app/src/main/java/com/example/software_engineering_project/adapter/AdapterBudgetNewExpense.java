package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.controller.FragmentBudgetAddExpenseScreenController;
import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.User;

import java.util.List;

public class AdapterBudgetNewExpense extends ArrayAdapter<User> {

    List<User> list;
    CheckBox checkBoxNewExpenseParticipants;
    Context context;

    public AdapterBudgetNewExpense(Context context, List<User> items) {
        super(context, R.layout.adapter_budget_new_expense, items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_new_expense, null);

            loadScreenElements(convertView);

            User user = list.get(position);
            checkBoxNewExpenseParticipants.setText(user.getFirstName());

            checkBoxNewExpenseParticipants.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checkBoxNewExpenseParticipants.isSelected()) {
                        FragmentBudgetAddExpenseScreenController.addSelectedUser(user);
                    } else {
                        FragmentBudgetAddExpenseScreenController.deleteSelectedUser(user);
                    }
                }
            });
        }

        return convertView;
    }

    private void loadScreenElements(View convertView) {

        checkBoxNewExpenseParticipants = convertView.findViewById(R.id.checkBoxNewExpenseParticipants);
    }


}
