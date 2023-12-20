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

    /**
     * Custom adapter for populating a Spinner widget with a list of strings.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The list of strings to be displayed in the Spinner.
     */
    public AdapterSpinnerList(Context context, ArrayList<String> items) {

        super(context, R.layout.adapter_grocery_list_list_view, items);
        this.context = context;
        list = items;

    }

    /**
     * Get a View that displays the data at the specified position in the adapter.
     * This method is responsible for creating or recycling item views for the Spinner items.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible. Note: this may be null initially.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
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

    /**
     * Loads and initializes screen elements for the View being processed.
     *
     * @param convertView The View in which elements are to be initialized.
     */
    private void loadScreenElements(View convertView) {

        nameOfInterval = convertView.findViewById(R.id.nameOfInterval);

    }

    /**
     * Callback method to be invoked when an item in the AdapterView has been selected.
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
     * Callback method to be invoked when the selection disappears from this view.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Sets the text of the nameOfInterval TextView with the provided itemSelected String.
     *
     * @param itemSelected The String to be set as the text for the nameOfInterval TextView.
     */
    public static void setText(String itemSelected) {
        nameOfInterval.setText(itemSelected);
    }

}