package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.software_engineering_project.R;

import java.util.ArrayList;

public class SpinnerListAdapter extends ArrayAdapter<String> implements AdapterView.OnItemSelectedListener {

    private static TextView nameOfInterval;
    private Context context;
    private ArrayList<String> list;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public SpinnerListAdapter(Context context, ArrayList<String> items) {

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
            convertView = mInflater.inflate(R.layout.adapter_spinner_list_view, null);

            loadScreenElements(convertView);

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        nameOfInterval = convertView.findViewById(R.id.nameOfInterval);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void setText(String itemSelected) {
        nameOfInterval.setText(itemSelected);
    }

}