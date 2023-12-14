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
import com.example.software_engineering_project.entity.Cleaning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterCleaningPlanListDetailView extends ArrayAdapter<Cleaning> {

    private Context context;
    private ImageView done, remove;
    private List<Cleaning> list;
    private TextView name, number;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public AdapterCleaningPlanListDetailView(Context context, List<Cleaning> items) {
        super(context, R.layout.adapter_cleaning_plan_list_detail_view, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_cleaning_plan_list_detail_view, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ".");

            // Format Date
            Date date = list.get(position).getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String monthName = monthNameFormat.format(date);
            String formattedDate = dateFormat.format(date);
            String yearName = yearFormat.format(date);

            String userName = list.get(position).getUser().getDisplayName();

            // Set the formatted
            name.setText(userName + ", " +formattedDate + " " + monthName + " " + yearName);

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
                //FragmentCleaningPlanListDetailController.removeItem(position);
            }
        });

    }

    private void loadScreenElements(View convertView) {

        done = convertView.findViewById(R.id.doneCleaning);
        name = convertView.findViewById(R.id.nameCleaner);
        number = convertView.findViewById(R.id.numberCleaningPlan);
        remove = convertView.findViewById(R.id.removeCleaning);

    }

}