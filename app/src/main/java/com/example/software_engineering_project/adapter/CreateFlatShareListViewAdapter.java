package com.example.software_engineering_project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.controller.FragmentManageFlatShareController;

public class CreateFlatShareListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public CreateFlatShareListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.adapter_manage_flat_share_list_view, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_manage_flat_share_list_view, null);

            TextView name = convertView.findViewById(R.id.nameCleaningPlan);
            ImageView remove = convertView.findViewById(R.id.removeCleaningPlan);
            TextView number = convertView.findViewById(R.id.numberCleaningPlan);

            number.setText(position + 1 + ".");
            name.setText(list.get(position));

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManageFlatShareController.removeItem(position);
                }
            });

        }

        return convertView;

    }

}