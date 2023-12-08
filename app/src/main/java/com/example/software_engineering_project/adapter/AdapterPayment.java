package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Payment;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class AdapterPayment extends ArrayAdapter<Payment> {

    List<Payment> paymentList;
    Context context;

    public AdapterPayment(Context context, List<Payment> payments) {
        super(context, R.layout.adapter_payment, payments);
        this.context = context;
        this.paymentList = payments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_payment, null);

            TextView expenseDescription = convertView.findViewById(R.id.expenseDescription);
            TextView expensePayer = convertView.findViewById(R.id.expensePayer);
            TextView expenseAmount = convertView.findViewById(R.id.expenseAmount);
            TextView expenseDate = convertView.findViewById(R.id.expenseDate);

            Payment currentPayment = paymentList.get(position);

            //TODO es funktionieren noch nicht alle Felder

            // Customize the layout based on the Payment details
            expenseDescription.setText(currentPayment.getName());
            expensePayer.setText("Test " + currentPayment.getCreatedByUser().getFirstName());
            expenseAmount.setText(String.format(Locale.getDefault(), "- %s", currentPayment.getAmount()));
            expenseDate.setText("Test " + currentPayment.getCreatedAt().toString());
        }

        return convertView;
    }
}
