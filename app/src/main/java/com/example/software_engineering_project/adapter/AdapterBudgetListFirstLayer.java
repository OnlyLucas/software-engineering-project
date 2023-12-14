package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.viewmodel.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for displaying the first layer of the budget list, where each item represents a month.
 * This adapter is designed to work with the AdapterBudgetListFirstLayer layout.
 *
 * The adapter provides functionality to display a list of months and associated payments in a second-layer list.
 *
 * Additionally, the getView method is responsible for inflating the layout, populating it with month data,
 * and initializing the AdapterBudgetListSecondLayer to display payments related to the selected month.
 *
 */
public class AdapterBudgetListFirstLayer extends ArrayAdapter<String> {

    private static AdapterBudgetListSecondLayer adapter;
    private static ArrayList<String> items = new ArrayList<>();
    private static PaymentRepository paymentRepository;
    private Context context;
    private List<String> list;
    private ListView budgetListSecondLayer;
    private LiveData<List<Payment>> currentPayments;
    private TextView showMonth;


    /**
     * Creates a new AdapterBudgetListFirstLayer.
     *
     * @param context          The context in which the adapter is being used.
     * @param items            The List containing months to be displayed in the first layer.
     * @param currentPayments  LiveData representing the current payments associated with the budget.
     */
    public AdapterBudgetListFirstLayer(Context context, List<String> items, LiveData<List<Payment>> currentPayments) {

        super(context, R.layout.adapter_budget_list_view_first_layer, items);
        this.context = context;
        this.list = items;
        this.currentPayments = currentPayments;

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
            convertView = mInflater.inflate(R.layout.adapter_budget_list_view_first_layer, null);

            loadScreenElements(convertView);

            showMonth.setText(list.get(position));

            List<Payment> payments = currentPayments.getValue();
            adapter = new AdapterBudgetListSecondLayer(context, payments);

            budgetListSecondLayer.setAdapter(adapter);

        }

        return convertView;

    }

    /**
     * Load screen elements from the layout.
     *
     * @param convertView The base view for the layout.
     */
    private void loadScreenElements(View convertView) {

        budgetListSecondLayer = convertView.findViewById(R.id.budgetListSecondLayer);
        showMonth = convertView.findViewById(R.id.showMonth);

    }

}
