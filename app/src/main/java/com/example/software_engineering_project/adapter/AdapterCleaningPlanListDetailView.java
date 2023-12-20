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
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanListDetailController;
import com.example.software_engineering_project.entity.Cleaning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Custom adapter for displaying details of cleaning plans as a List.
 *
 * This adapter extends ArrayAdapter<Cleaning> and is designed to work with the layout 'adapter_cleaning_plan_list_detail_view'.
 * It provides a customized view for each item in the ListView, including a number, formatted date, user name, and buttons for
 * marking the cleaning as done or removing it.
 *
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the Cleaning object
 * at the specified position in the data set. Additionally, it adds listeners for the done and remove buttons.
 */
public class AdapterCleaningPlanListDetailView extends ArrayAdapter<Cleaning> {

    private Context context;
    private ImageView done, remove;
    private List<Cleaning> list;
    private TextView date, name, number;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List

    /**
     * Constructs an Adapter for a Cleaning Plan List Detail View.
     *
     * @param context The context of the application.
     * @param items   The list of Cleaning objects to be displayed in the adapter.
     */
    public AdapterCleaningPlanListDetailView(Context context, List<Cleaning> items) {
        super(context, R.layout.adapter_cleaning_plan_list_detail_view, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * This method creates or reuses a view to represent an item in the adapter's data set. It inflates the layout
     * 'adapter_cleaning_plan_list_detail_view' if the provided convertView is null, and then loads screen elements
     * using the loadScreenElements method. It sets the text of the number TextView, formatted date, and user name,
     * and adds listeners for the done and remove buttons.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_cleaning_plan_list_detail_view, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ".");

            // Format Date
            Date dateInput = list.get(position).getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMM");
            String monthName = monthNameFormat.format(dateInput);
            String formattedDate = dateFormat.format(dateInput);

            String userName = list.get(position).getUser().getDisplayName();

            // Set the formatted
            name.setText(userName);
            date.setText(formattedDate + " " + monthName);

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
                FragmentCleaningPlanListDetailController.removeItem(position);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCleaningPlanListDetailController.uncheckItem(position);
            }
        });

    }

    /**
     * Initializes the UI elements within the provided view for displaying cleaning plan details.
     *
     * @param convertView The view in which the UI elements are to be initialized.
     */
    private void loadScreenElements(View convertView) {

        date = convertView.findViewById(R.id.cleaningDate);
        done = convertView.findViewById(R.id.doneCleaning);
        name = convertView.findViewById(R.id.nameCleaner);
        number = convertView.findViewById(R.id.numberCleaningPlan);
        remove = convertView.findViewById(R.id.removeCleaning);

    }

}