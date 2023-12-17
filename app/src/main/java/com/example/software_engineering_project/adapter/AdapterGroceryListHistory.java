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
import com.example.software_engineering_project.controller.groceries.FragmentGroceryListHistoryController;
import com.example.software_engineering_project.entity.GroupGrocery;

import java.util.List;


public class AdapterGroceryListHistory extends ArrayAdapter<GroupGrocery> {

    private Context context;
    private ImageView remove;
    private List<GroupGrocery> list;
    private TextView name, number;


    public AdapterGroceryListHistory(Context context, List<GroupGrocery> items) {

        super(context, R.layout.adapter_grocery_list_history, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_grocery_list_history, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ".");
            GroupGrocery current = list.get(position);
            name.setText(current.getName());

            addButtons(position);

        }

        return convertView;

    }

    private void addButtons(int position) {

        // Listeners for duplicating and removing an item.
        // They use the static removeItem and addItem methods created in MainActivity.
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentGroceryListHistoryController.removeItem(position);
            }
        });

    }

    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameGroceryListHistory);
        number = convertView.findViewById(R.id.numberGroceryListHistory);
        remove = convertView.findViewById(R.id.removeGroceryListHistory);

    }

}