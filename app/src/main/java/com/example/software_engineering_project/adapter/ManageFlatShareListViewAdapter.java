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

import com.example.software_engineering_project.controller.ManageFlatShareController;
import com.example.software_engineering_project.R;

import java.util.ArrayList;

public class ManageFlatShareListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public ManageFlatShareListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.activity_mangage_flat_share_list_view_adapter, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_mangage_flat_share_list_view_adapter, null);
            TextView name = convertView.findViewById(R.id.name);
            ImageView remove = convertView.findViewById(R.id.remove);
            TextView number = convertView.findViewById(R.id.number);


            number.setText(position + 1 + ".");
            name.setText(list.get(position));

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ManageFlatShareController.removeItem(position);
                }
            });
        }
        return convertView;
    }

}