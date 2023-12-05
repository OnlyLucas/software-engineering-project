package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.controller.FragmentGroceryListController;
import com.example.software_engineering_project.R;

public class GroceryListListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;

    public GroceryListListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.adapter_grocery_list_list_view, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_grocery_list_list_view, null);

            TextView name = convertView.findViewById(R.id.nameGroceryList);
            ImageView remove = convertView.findViewById(R.id.removeGroceryList);
            ImageView unchecked = convertView.findViewById(R.id.uncheckedGroceryList);
            TextView number = convertView.findViewById(R.id.numberGroceryList);

            number.setText(position + 1 + ".");
            name.setText(list.get(position));

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentGroceryListController.removeItem(position);
                }
            });

            unchecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentGroceryListController.uncheckItem(position);
                }
            });

        }

        return convertView;

    }

}