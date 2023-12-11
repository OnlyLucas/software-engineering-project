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

public class AdapterBudgetListFirstLayer extends ArrayAdapter<String> {

    private static AdapterBudgetListSecondLayer adapter;
    private static ArrayList<String> items = new ArrayList<>();
    private static PaymentRepository paymentRepository;
    private ListView budgetListSecondLayer;
    private Context context;
    private LiveData<List<Payment>> currentPayments;
    private List<String> list;
    private TextView showMonth;

    public AdapterBudgetListFirstLayer(Context context, List<String> items, LiveData<List<Payment>> currentPayments) {

        super(context, R.layout.adapter_budget_list_view_first_layer, items);
        this.context = context;
        this.list = items;
        this.currentPayments = currentPayments;

    }

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

    private void loadScreenElements(View convertView) {

        budgetListSecondLayer = convertView.findViewById(R.id.budgetListSecondLayer);
        showMonth = convertView.findViewById(R.id.showMonth);

    }

}
