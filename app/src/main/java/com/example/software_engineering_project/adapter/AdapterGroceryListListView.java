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
 * <p>
 * This adapter extends ArrayAdapter<GroupGrocery> and is designed to work with the layout 'adapter_grocery_list_list_view'.
 * It provides a customized view for each item in the ListView, including a number, group name, and buttons for removing and unchecking the group.
 * <p>
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the GroupGrocery object at the specified position in the data set. Additionally, it adds listeners for the remove and unchecked buttons.
 */
public class AdapterGroceryListListView extends ArrayAdapter<GroupGrocery> {

    private Context context;
    private ImageView remove, unchecked;
    private List<GroupGrocery> list;
    private TextView name, number;


    /**
     * Constructs an AdapterGroceryListListView object.
     * This adapter binds a List of GroupGrocery items to a ListView in the application's UI.
     * It helps display these items within the specified layout for the grocery list list view.
     *
     * @param context The context in which the adapter is being used, usually an Activity context.
     *                It's used to access resources and system information.
     * @param items   A List of GroupGrocery items to be displayed in the grocery list list view.
     *                It contains the data to be adapted and shown in the ListView.
     */
    public AdapterGroceryListListView(Context context, List<GroupGrocery> items) {

        super(context, R.layout.adapter_grocery_list_list_view, items);
        this.context = context;
        list = items;

    }

    /**
     * This method is responsible for rendering the view for each item in the grocery list list view.
     * It populates the ListView with the required data by binding it to the specified layout.
     * If the convertView (a recycled view) is null, a new view is inflated and data is loaded.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The view representing an item in the ListView.
     * @param parent      The parent view that this view will eventually be attached to.
     * @return The view for the ListView item at the specified position.
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

    /**
     * Attaches click listeners to the remove and unchecked buttons for a specific item in the grocery list.
     * The remove button removes the item from the list, and the unchecked button unchecks the item if it was previously checked.
     *
     * @param position The position of the item within the adapter's data set.
     */
    private void addButtons(int position) {

        remove.setOnClickListener(view -> FragmentGroceryListController.removeItem(position));
        unchecked.setOnClickListener(view -> FragmentGroceryListController.uncheckItem(position));

    }

    /**
     * Finds and assigns views for various elements present in the grocery list item layout.
     *
     * @param convertView The view representing a single item in the grocery list.
     */
    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameGroceryList);
        number = convertView.findViewById(R.id.numberGroceryList);
        remove = convertView.findViewById(R.id.removeGroceryList);
        unchecked = convertView.findViewById(R.id.uncheckedGroceryList);

    }

}