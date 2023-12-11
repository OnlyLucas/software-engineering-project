package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Payment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterBudgetListSecondLayer extends ArrayAdapter<Payment> {

    private List<Payment> list;

    private Context context;

    public AdapterBudgetListSecondLayer(Context context, List<Payment> items) {
        super(context, R.layout.adapter_budget_list_view_second_layer, items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_list_view_second_layer, null);

            TextView expenseDescription = convertView.findViewById(R.id.expenseDescription);
            TextView expensePayer = convertView.findViewById(R.id.expensePayer);
            TextView expenseAmount = convertView.findViewById(R.id.expenseAmount);
            TextView expenseDate = convertView.findViewById(R.id.expenseDate);

            Payment currentPayment = list.get(position);

            // Customize the layout based on the Payment details
            expenseDescription.setText(currentPayment.getName());
            expensePayer.setText(currentPayment.getCreatedByUser().getFirstName() + " paid");
            expenseAmount.setText(String.format(Locale.getDefault(), "- %s", currentPayment.getAmount()));

            // Convert Timestamp to other date format
            Timestamp timestamp = currentPayment.getCreatedAt();
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM");
            String monthName = monthNameFormat.format(date);
            String formattedDate = dateFormat.format(date);

            // Set the formatted
            expenseDate.setText(formattedDate + " " + monthName);
        }

        return convertView;
    }
}

