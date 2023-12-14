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
import androidx.lifecycle.LifecycleOwner;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.FragmentCleaningPlanListController;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.viewmodel.CleaningRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterCleaningPlanListView extends ArrayAdapter<CleaningTemplate> {

    private Context context;
    private ImageView remove;
    private List<CleaningTemplate> list;
    private TextView name, nextCleaningDate, number;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public AdapterCleaningPlanListView(Context context, List<CleaningTemplate> items) {

        super(context, R.layout.adapter_cleaning_plan_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            CleaningTemplate cleaningTemplate = list.get(position);

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_cleaning_plan_list_view, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ".");
            name.setText(cleaningTemplate.getName());

            CleaningRepository cleaningRepository = new CleaningRepository();
            cleaningRepository.getUncompletedCleanings(cleaningTemplate.getId()).observe((LifecycleOwner) context, cleanings -> {
                if (cleanings != null && !cleanings.isEmpty()) {
                    Cleaning nextCleaning = cleanings.get(0);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
                    String formattedDate = dateFormat.format(nextCleaning.getDate());
                    nextCleaningDate.setText(formattedDate);
                } else {
                    System.out.println("Error to get date of next cleaning.");
                }
            });

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
        nextCleaningDate = convertView.findViewById(R.id.nextCleaningDate);
        remove = convertView.findViewById(R.id.removeCleaningPlan);

    }

}