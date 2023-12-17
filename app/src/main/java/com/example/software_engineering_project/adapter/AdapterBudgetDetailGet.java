package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.budget.FragmentBudgetDetailScreenController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * Custom adapter for displaying budget details as a list related to received amounts.
 * This adapter is designed to work with the AdapterBudgetDetailGet layout.
 *
 * The adapter provides functionality to display a list of users with associated debt amounts
 * and allows users to be marked as "checked" via the checkExpenseGet ImageView.
 *
 * Additionally, when the checkExpenseGet ImageView is clicked, it triggers an onClick event
 * to uncheck the corresponding item through FragmentBudgetDetailScreenController.
 *
 */
public class AdapterBudgetDetailGet extends ArrayAdapter<Object[]> {

    private List<Object[]> list;
    private Context context;
    private ImageView checkExpenseGet;
    private TextView amountOfDebt, nameOfDebtor;

    /**
     * Creates a new AdapterBudgetDetailGet.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The List containing Object[] pairs where the first element is a LinkedHashMap representing a user,
     *                and the second element is the total amount of debt associated with that user.
     */
    public AdapterBudgetDetailGet(Context context, List<Object[]> items) {

        super(context, R.layout.adapter_budget_detail_get, items);
        this.context = context;
        list = items;

    }

    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_detail_get, null);
            loadScreenElements(convertView);

            Object[] pair = list.get(position);
            LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

            Double totalAmount = (Double) pair[1];
            String name = (String) userMap.get("firstName");

            nameOfDebtor.setText(name);

            // So far we only support euro as currency, but in this place a differentiation would be needed
            String amount = String.format(Locale.getDefault(), "%.2f", totalAmount) + "€";
            amountOfDebt.setText(amount);

            checkExpenseGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentBudgetDetailScreenController.uncheckItemGet(position, context);
                }
            });

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        checkExpenseGet = convertView.findViewById(R.id.checkExpenseGet);
        amountOfDebt = convertView.findViewById(R.id.amountOfDebt);
        nameOfDebtor = convertView.findViewById(R.id.nameOfDebtor);

    }

}