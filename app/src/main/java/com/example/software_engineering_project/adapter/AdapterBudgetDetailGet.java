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
     * Constructs an AdapterBudgetDetailGet instance.
     * <p>
     * This constructor initializes the AdapterBudgetDetailGet with the provided context and a list of items.
     * <p>
     * Example usage:
     * {@code AdapterBudgetDetailGet adapter = new AdapterBudgetDetailGet(context, itemsList);}
     * <p>
     * @param context The context in which the adapter will be used.
     * @param items   The list of items to be displayed in the adapter.
     */
    public AdapterBudgetDetailGet(Context context, List<Object[]> items) {

        super(context, R.layout.adapter_budget_detail_get, items);
        this.context = context;
        list = items;

    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * <p>
     * This method creates and returns a View displaying the data located at the specified position
     * in the adapter's data set. If a convertView is available, it is reused; otherwise, a new View is inflated.
     * <p>
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return            A View corresponding to the data at the specified position.
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

            checkExpenseGet.setOnClickListener(view -> FragmentBudgetDetailScreenController.uncheckItemGet(position, context));

        }

        return convertView;

    }

    /**
     * Loads and initializes screen elements for the specified view.
     * <p>
     * This method initializes specific UI elements present in the provided view,
     * such as buttons, text views, or any other UI components used within the layout.
     * These elements are then assigned to their respective class variables for future use.
     * <p>
     * @param convertView The view containing the UI elements to be initialized.
     */
    private void loadScreenElements(View convertView) {

        checkExpenseGet = convertView.findViewById(R.id.checkExpenseGet);
        amountOfDebt = convertView.findViewById(R.id.amountOfDebt);
        nameOfDebtor = convertView.findViewById(R.id.nameOfDebtor);

    }

}