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
import java.util.ArrayList;

import com.example.software_engineering_project.controller.FragmentCleaningPlanController;
import com.example.software_engineering_project.controller.FragmentCleaningPlanListController;
import com.example.software_engineering_project.controller.FragmentGroceryListController;
import com.example.software_engineering_project.R;

public class CleaningPlanListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public CleaningPlanListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.cleaning_plan_list_view_adapter, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.cleaning_plan_list_view_adapter, null);

            TextView name = convertView.findViewById(R.id.name);
            ImageView remove = convertView.findViewById(R.id.remove);
            ImageView unchecked = convertView.findViewById(R.id.unchecked);
            ImageView copy = convertView.findViewById(R.id.copy);
            TextView number = convertView.findViewById(R.id.number);


            number.setText(position + 1 + ".");
            name.setText(list.get(position));

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentCleaningPlanListController.removeItem(position);
                }
            });

            unchecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentCleaningPlanListController.uncheckItem(position);
                }
            });

            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentCleaningPlanListController.addItem(list.get(position));
                }
            });

        }

        return convertView;

    }

}