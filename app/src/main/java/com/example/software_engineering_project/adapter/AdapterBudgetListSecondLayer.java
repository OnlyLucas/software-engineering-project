package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.Payment;

import java.util.List;

public class AdapterBudgetListSecondLayer extends ArrayAdapter<Payment> {

    List<Payment> list;

    Context context;

    public AdapterBudgetListSecondLayer (Context context, List<Payment> items) {
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

            System.out.println(list.toString());

            //TODO wie bekommt man mehr als nur das erste Payment angezeigt?

            // Create an adapter for the list of payments
            AdapterPayment adapterPayment = new AdapterPayment(context, list);

            // Set the adapter for the ListView
            ListView paymentsListView = convertView.findViewById(R.id.paymentsListView);
            paymentsListView.setAdapter(adapterPayment);
        }
        return convertView;
    }
}

