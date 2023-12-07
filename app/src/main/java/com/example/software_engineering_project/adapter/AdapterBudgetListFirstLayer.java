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
import com.example.software_engineering_project.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class AdapterBudgetListFirstLayer extends ArrayAdapter<Payment>{

    List<Payment> list;
    TextView showMonth;
    ListView budgetListFirstLayer;

    Context context;

    static ArrayList<String> items = new ArrayList<>();

    static AdapterBudgetListSecondLayer adapter;

    public AdapterBudgetListFirstLayer(Context context, List<Payment> items) {
        super(context, R.layout.adapter_budget_list_view_first_layer, items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_list_view_first_layer, null);
            loadScreenElements(convertView);
            showMonth.setText(list.get(position).getName());

            View convertView2 = mInflater.inflate(R.layout.adapter_budget_list_view_second_layer, null);
            ListView budgetListSecondLayer = convertView.findViewById(R.id.budgetListSecondLayer);
            TextView showMonth2 = convertView2.findViewById(R.id.expenseDescription);

            items.add("January");
            items.add("February");
            items.add("March");
            items.add("April");
            items.add("May");
            items.add("June");
            items.add("July");
            items.add("August");
            adapter = new AdapterBudgetListSecondLayer(context, items);

            budgetListSecondLayer.setAdapter(adapter);
            showMonth2.setText(items.get(position));
        }

        return convertView;
    }

    private void loadScreenElements(View convertView) {
        showMonth = convertView.findViewById(R.id.showMonth);
        budgetListFirstLayer = convertView.findViewById(R.id.budgetListFirstLayer);
    }


}
