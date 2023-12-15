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
import com.example.software_engineering_project.controller.groceries.FragmentGroceryListController;
import com.example.software_engineering_project.entity.GroupGrocery;

import java.util.List;

/**
 * Custom adapter for displaying a list of grocery groups in a ListView.
 *
 * This adapter extends ArrayAdapter<GroupGrocery> and is designed to work with the layout 'adapter_grocery_list_list_view'.
 * It provides a customized view for each item in the ListView, including a number, group name, and buttons for removing and unchecking the group.
 *
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the GroupGrocery object at the specified position in the data set. Additionally, it adds listeners for the remove and unchecked buttons.
 */
public class AdapterGroceryListListView extends ArrayAdapter<GroupGrocery> {

    private Context context;
    private ImageView remove, unchecked;
    private List<GroupGrocery> list;
    private TextView name, number;


    /**
     * The constructor for the AdapterGroceryListListView.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The List of GroupGrocery objects to be displayed in the adapter.
     */
    public AdapterGroceryListListView(Context context, List<GroupGrocery> items) {

        super(context, R.layout.adapter_grocery_list_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * This method creates or reuses a view to represent an item in the adapter's data set. It inflates the layout
     * 'adapter_grocery_list_list_view' if the provided convertView is null, and then loads screen elements using the loadScreenElements method. It sets the text of the number, group name TextViews, and adds listeners for the remove and unchecked buttons.
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
            convertView = mInflater.inflate(R.layout.adapter_grocery_list_list_view, null);

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

    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameGroceryList);
        number = convertView.findViewById(R.id.numberGroceryList);
        remove = convertView.findViewById(R.id.removeGroceryList);
        unchecked = convertView.findViewById(R.id.uncheckedGroceryList);

    }

}