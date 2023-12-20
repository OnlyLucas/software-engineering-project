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
import com.example.software_engineering_project.controller.budget.FragmentBudgetListController;
import com.example.software_engineering_project.entity.Payment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Custom adapter for displaying the first layer of the budget list, where each item represents a payment.
 * This adapter is designed to work with the AdapterBudgetListFirstLayer layout.
 * <p>
 * The adapter provides functionality to display a list of payments in a specified format in the first layer.
 * <p>
 * Additionally, the getView method is responsible for inflating the layout, populating it with payment details,
 * and customizing the layout based on the Payment details such as name, payer, amount, and date.
 */
public class AdapterBudgetListFirstLayer extends ArrayAdapter<Payment> {

    private Context context;
    private List<Payment> list;
    private ImageView removeExpense;
    private TextView expenseAmount, expenseDate, expenseDescription, expensePayer;

    /**
     * Adapter for managing the first layer of the budget list view.
     * <p>
     * This adapter handles the initial layer of the budget list view by binding Payment items to the view.
     *
     * @param context The context in which the adapter is used.
     * @param items   List of Payment items to be displayed in the adapter.
     */
    public AdapterBudgetListFirstLayer(Context context, List<Payment> items) {

        super(context, R.layout.adapter_budget_list_view_first_layer, items);
        this.context = context;
        list = items;

    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_list_view_first_layer, null);

            loadScreenElements(convertView);

            Payment currentPayment = list.get(position);

            // Customize the layout based on the Payment details
            expenseDescription.setText(currentPayment.getName());
            expensePayer.setText(currentPayment.getCreatedByUser().getFirstName() + " " + context.getString(R.string.paid));
            String amount = String.format(Locale.getDefault(), "- %.2f", currentPayment.getAmount());
            // So far we only support euro as currency, but in this place a differentiation would be needed
            if (currentPayment.getCurrencyCode().equals("EUR")) {
                amount = amount + "€";
            }
            expenseAmount.setText(amount);

            // Convert Timestamp to other date format
            Timestamp timestamp = currentPayment.getCreatedAt();
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMM");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
            String monthName = monthNameFormat.format(date);
            String formattedDate = dateFormat.format(date);
            String yearName = yearFormat.format(date);

            // Set the formatted date
            expenseDate.setText(formattedDate + " " + monthName + " " + yearName);

            addButtons(position);

        }

        return convertView;

    }

    /**
     * Adds functionality to buttons in the view.
     *
     * @param position The position of the item.
     */
    private void addButtons(int position) {
        removeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentBudgetListController.removeItem(position);
            }
        });

    }

    /**
     * Loads various screen elements from the given view.
     *
     * @param convertView The view containing the screen elements.
     */
    private void loadScreenElements(View convertView) {

        expenseAmount = convertView.findViewById(R.id.expenseAmount);
        expenseDate = convertView.findViewById(R.id.expenseDate);
        expenseDescription = convertView.findViewById(R.id.expenseDescription);
        expensePayer = convertView.findViewById(R.id.expensePayer);
        removeExpense = convertView.findViewById(R.id.removeExpense);

    }

}

