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

import java.util.ArrayList;

public class AdapterBudgetDetailOwe extends ArrayAdapter<String> {

    private ArrayList<String> list;
    private Context context;
    private ImageView checkExpenseOwe;
    private TextView amountOfCredit, nameOfCreditor;


    public AdapterBudgetDetailOwe(Context context, ArrayList<String> items) {

        super(context, R.layout.adapter_budget_detail_owe, items);
        this.context = context;
        list = items;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_detail_owe, null);
            loadScreenElements(convertView);

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        amountOfCredit = convertView.findViewById(R.id.amountOfCredit);
        checkExpenseOwe = convertView.findViewById(R.id.checkExpenseOwe);
        nameOfCreditor = convertView.findViewById(R.id.nameOfCreditor);

    }

}
