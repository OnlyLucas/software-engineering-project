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
import com.example.software_engineering_project.controller.FragmentCleaningPlanListController;
import com.example.software_engineering_project.entity.CleaningTemplate;

import java.util.List;

public class CleaningPlanListViewAdapter extends ArrayAdapter<CleaningTemplate> {

    private Context context;
    private List<CleaningTemplate> list;
    private TextView name, number;
    private ImageView remove;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public CleaningPlanListViewAdapter(Context context, List<CleaningTemplate> items) {

        super(context, R.layout.adapter_cleaning_plan_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_cleaning_plan_list_view, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ".");
            name.setText(list.get(position).getName());

            addButtons(position);

        }

        return convertView;

    }

    private void addButtons(int position) {

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCleaningPlanListController.removeItem(position);
            }
        });

    }

    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameCleaningPlan);
        number = convertView.findViewById(R.id.numberCleaningPlan);
        remove = convertView.findViewById(R.id.removeCleaningPlan);

    }

}