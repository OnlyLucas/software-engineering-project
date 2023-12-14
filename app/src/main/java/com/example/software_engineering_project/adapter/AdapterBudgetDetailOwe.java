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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Custom adapter for displaying budget details related to owed amounts.
 * This adapter is designed to work with the AdapterBudgetDetailOwe layout.
 *
 * The adapter provides functionality to display a list of users with associated credit amounts
 * and allows users to be marked as "checked" via the checkExpenseOwe ImageView.
 *
 * Additionally, the getView method is responsible for inflating the layout and populating it with
 * user data obtained from the list of Object[] pairs.
 */
public class AdapterBudgetDetailOwe extends ArrayAdapter<Object[]> {

    private List<Object[]> list;
    private Context context;
    private ImageView checkExpenseOwe;
    private TextView amountOfCredit, nameOfCreditor;


    /**
     * Creates a new AdapterBudgetDetailOwe.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The List containing Object[] pairs where the first element is a LinkedHashMap representing a user,
     *                and the second element is the total amount of credit associated with that user.
     */
    public AdapterBudgetDetailOwe(Context context, List<Object[]> items) {

        super(context, R.layout.adapter_budget_detail_owe, items);
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
            convertView = mInflater.inflate(R.layout.adapter_budget_detail_owe, null);
            loadScreenElements(convertView);

            Object[] pair = list.get(position);
            LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) pair[0];

            Double totalAmount = (Double) pair[1];
            String name = (String) userMap.get("firstName");

            nameOfCreditor.setText(name);
            amountOfCredit.setText(totalAmount.toString());

        }

        return convertView;

    }

    /**
     * Load screen elements from the layout.
     *
     * @param convertView The base view for the layout.
     */
    private void loadScreenElements(View convertView) {

        amountOfCredit = convertView.findViewById(R.id.amountOfCredit);
        checkExpenseOwe = convertView.findViewById(R.id.checkExpenseOwe);
        nameOfCreditor = convertView.findViewById(R.id.nameOfCreditor);

    }

}
