package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;

import java.util.ArrayList;

public class AdapterBudgetDetailGet extends ArrayAdapter<String> {

    private ArrayList<String> list;
    private Context context;
    private ImageView checkExpenseGet;
    private TextView amountOfDebt, nameOfDebtor;


    public AdapterBudgetDetailGet(Context context, ArrayList<String> items) {

        super(context, R.layout.adapter_budget_detail_get, items);
        this.context = context;
        list = items;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_budget_detail_get, null);
            loadScreenElements(convertView);

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        checkExpenseGet = convertView.findViewById(R.id.checkExpenseGet);
        amountOfDebt = convertView.findViewById(R.id.amountOfDebt);
        nameOfDebtor = convertView.findViewById(R.id.nameOfDebtor);

    }

}