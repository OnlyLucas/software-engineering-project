package com.example.software_engineering_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import com.example.software_engineering_project.controller.cleanings.FragmentCleaningPlanListController;
import com.example.software_engineering_project.entity.Cleaning;
import com.example.software_engineering_project.entity.CleaningTemplate;
import com.example.software_engineering_project.viewmodel.CleaningRepository;

import java.util.List;

/**
 * Custom adapter for displaying a list of cleaning plans in a ListView.
 *
 * This adapter extends ArrayAdapter<CleaningTemplate> and is designed to work with the layout 'adapter_cleaning_plan_list_view'.
 * It provides a customized view for each item in the ListView, including a number, cleaning template name, next cleaning date, and a button to remove the cleaning plan.
 *
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the CleaningTemplate object at the specified position in the data set. Additionally, it adds a listener for the remove button.
 */
public class AdapterCleaningPlanListView extends ArrayAdapter<CleaningTemplate> {

    private Context context;
    private ImageView remove;
    private List<CleaningTemplate> list;
    private TextView name, nextCleaningDate, number;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    /**
     * The constructor for the AdapterCleaningPlanListView.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The List of CleaningTemplate objects to be displayed in the adapter.
     */
    public AdapterCleaningPlanListView(Context context, List<CleaningTemplate> items) {

        super(context, R.layout.adapter_cleaning_plan_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * This method creates or reuses a view to represent an item in the adapter's data set. It inflates the layout
     * 'adapter_cleaning_plan_list_view' if the provided convertView is null, and then loads screen elements using the loadScreenElements method. It sets the text of the number, cleaning template name, and next cleaning date TextViews, and adds a listener for the remove button.
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