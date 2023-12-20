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

/**
 * Adapter for displaying grocery list history items in a ListView.
 * This adapter manages the data items and creates views for each item in the list.
 */
public class AdapterGroceryListHistory extends ArrayAdapter<GroupGrocery> {

    private Context context;
    private ImageView remove;
    private List<GroupGrocery> list;
    private TextView name, number;

    /**
     * Constructor for the AdapterGroceryListHistory class.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The list of GroupGrocery items to be displayed.
     */
    public AdapterGroceryListHistory(Context context, List<GroupGrocery> items) {

        super(context, R.layout.adapter_grocery_list_history, items);
        this.context = context;
        list = items;

    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * This method is responsible for creating and populating the views for each item
     * in the grocery list history.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible. This parameter can be null
     *                    initially or when the previous view cannot be reused.
     * @param parent      The parent ViewGroup that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     * This view will be displayed as an item in the grocery list history.
     */
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

    /**
     * Sets up the OnClickListener for the remove button in the grocery list history item.
     * When clicked, this button invokes the {@link FragmentGroceryListHistoryController#removeItem(int)}
     * method to remove the corresponding item at the given position.
     *
     * @param position The position of the item within the grocery list history.
     *                 It determines which item's remove button is clicked.
     */
    private void addButtons(int position) {
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentGroceryListHistoryController.removeItem(position);
            }
        });

    }

    /**
     * Initializes UI elements by finding views from the provided convertView layout.
     * This method locates specific views within the grocery list history item layout.
     *
     * @param convertView The view representing a single item in the grocery list history ListView.
     *                    It's used to find and reference UI elements within that item.
     */
    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameGroceryListHistory);
        number = convertView.findViewById(R.id.numberGroceryListHistory);
        remove = convertView.findViewById(R.id.removeGroceryListHistory);

    }

}