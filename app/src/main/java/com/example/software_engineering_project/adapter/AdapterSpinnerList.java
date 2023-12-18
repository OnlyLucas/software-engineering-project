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

/**
 * Custom adapter for displaying a list of items in a Spinner view.
 *
 * This adapter extends ArrayAdapter<String> and is designed to work with the layout 'adapter_spinner_list_view'.
 * It provides a customized view for each item in the Spinner, including the name of the interval.
 *
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the String item at the specified position in the data set.
 * The adapter also implements the AdapterView.OnItemSelectedListener interface to handle item selection events.
 */
public class AdapterSpinnerList extends ArrayAdapter<String> implements AdapterView.OnItemSelectedListener {

    private static TextView nameOfInterval;
    private ArrayList<String> list;
    private Context context;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    /**
     * The constructor for the AdapterSpinnerList.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The ArrayList of String items to be displayed in the Spinner.
     */
    public AdapterSpinnerList(Context context, ArrayList<String> items) {

        super(context, R.layout.adapter_grocery_list_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * This method creates or reuses a view to represent an item in the adapter's data set. It inflates the layout
     * 'adapter_spinner_list_view' if the provided convertView is null, and then loads screen elements using the loadScreenElements method. It sets the text of the nameOfInterval TextView based on the String item at the specified position.
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
            convertView = mInflater.inflate(R.layout.adapter_spinner_list_view, null);

            loadScreenElements(convertView);

        }

        return convertView;

    }

    private void loadScreenElements(View convertView) {

        nameOfInterval = convertView.findViewById(R.id.nameOfInterval);

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has been selected.
     *
     * @param parent   The AdapterView where the selection happened.
     * @param view     The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that is selected.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * Callback method to be invoked when the selection disappears from this AdapterView.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Set the text of the nameOfInterval TextView.
     *
     * This method allows external classes to set the text of the nameOfInterval TextView.
     *
     * @param itemSelected The String item selected in the Spinner.
     */
    public static void setText(String itemSelected) {
        nameOfInterval.setText(itemSelected);
    }

}