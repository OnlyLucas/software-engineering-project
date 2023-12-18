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
 * Custom adapter for displaying a list of owed expenses in a budget detail screen.
 * The adapter is designed to work with a custom layout (R.layout.adapter_budget_detail_owe).
 */
public class AdapterBudgetDetailOwe extends ArrayAdapter<Object[]> {

    private List<Object[]> list;
    private Context context;
    private ImageView checkExpenseOwe;
    private TextView amountOfCredit, nameOfCreditor;


    /**
     * Constructs the adapter with the specified context and list of items.
     *
     * @param context The context.
     * @param items   The list of items to display.
     */
    public AdapterBudgetDetailOwe(Context context, List<Object[]> items) {

        super(context, R.layout.adapter_budget_detail_owe, items);
        this.context = context;
        list = items;

    }

    /**
     * This method is responsible for creating or recycling a view for an item in the Adapter.
     * If the convertView is null, a new view is inflated using the specified layout resource.
     * The data for the item at the given position is then set to the view elements.
     * Finally, a click listener is attached to the checkExpenseOwe ImageView for user interaction.
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
            // So far we only support euro as currency, but in this place a differentiation would be needed
            String amount = String.format(Locale.getDefault(), "- %.2f", totalAmount) + "€";
            amountOfCredit.setText(amount);
            checkExpenseOwe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentBudgetDetailScreenController.uncheckItemOwe(position, context);
                }
            });

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        amountOfCredit = convertView.findViewById(R.id.amountOfCredit);
        checkExpenseOwe = convertView.findViewById(R.id.checkExpenseOwe);
        nameOfCreditor = convertView.findViewById(R.id.nameOfCreditor);

    }

}
