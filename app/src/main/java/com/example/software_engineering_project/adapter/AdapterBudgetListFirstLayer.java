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

public class AdapterBudgetListFirstLayer extends ArrayAdapter<String> {

    ArrayList<String> list;
    TextView showMonth;
    ListView budgetListFirstLayer;

    Context context;

    public AdapterBudgetListFirstLayer(Context context, ArrayList<String> items) {
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
            showMonth.setText(list.get(position));
        }

        return convertView;
    }

    private void loadScreenElements(View convertView) {
        showMonth = convertView.findViewById(R.id.showMonth);
        budgetListFirstLayer = convertView.findViewById(R.id.budgetListFirstLayer);
    }


}
