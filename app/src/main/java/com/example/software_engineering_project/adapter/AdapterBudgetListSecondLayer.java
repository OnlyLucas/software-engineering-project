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

import com.example.software_engineering_project.R;

import java.util.ArrayList;

public class AdapterBudgetListSecondLayer extends ArrayAdapter<String> {

    ArrayList<String> list;
    static ArrayList<String> items = new ArrayList<>();

    static AdapterBudgetListSecondLayer adapter;

    Context context;

    public AdapterBudgetListSecondLayer (Context context, ArrayList<String> items) {
        super(context, R.layout.adapter_budget_list_view_second_layer);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_list_view_second_layer, null);

            TextView showMonth = convertView.findViewById(R.id.showMonth);
            ListView budgetListSecondLayer = convertView.findViewById(R.id.budgetListSecondLayer);

            adapter = new AdapterBudgetListSecondLayer(context, items);
            items.clear();
            items.add("January");
            items.add("February");
            items.add("March");
            items.add("April");
            items.add("May");
            items.add("June");
            items.add("July");
            items.add("August");
            budgetListSecondLayer.setAdapter(adapter);
            showMonth.setText(list.get(position));
        }

        return convertView;
    }
}
