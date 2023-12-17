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
import com.example.software_engineering_project.controller.appsettings.FragmentManageFlatShareController;
import com.example.software_engineering_project.entity.User;

import java.util.List;

/**
 * Custom adapter for displaying a list of users in a ListView for managing a flat share.
 *
 * This adapter extends ArrayAdapter<User> and is designed to work with the layout 'adapter_manage_flat_share_list_view'.
 * It provides a customized view for each item in the ListView, including a number, user name, and a button for removing the user.
 *
 * The getView method is overridden to inflate the layout, load screen elements, and set the content based on the User object at the specified position in the data set. Additionally, it adds a listener for the remove button.
 */
public class AdapterManageFlatShareListView extends ArrayAdapter<User> {

    private Context context;
    private ImageView remove;
    private List<User> list;
    private TextView name, number;


    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    /**
     * The constructor for the AdapterManageFlatShareListView.
     *
     * @param context The context in which the adapter is being used.
     * @param items   The List of User objects to be displayed in the adapter.
     */
    public AdapterManageFlatShareListView(Context context, List<User> items) {

        super(context, R.layout.adapter_manage_flat_share_list_view, items);
        this.context = context;
        list = items;

    }

    // The method we override to provide our own layout for each View (row) in the ListView
    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * This method creates or reuses a view to represent an item in the adapter's data set. It inflates the layout
     * 'adapter_manage_flat_share_list_view' if the provided convertView is null, and then loads screen elements using the loadScreenElements method. It sets the text of the number, user name TextViews, and adds a listener for the remove button.
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
            convertView = mInflater.inflate(R.layout.adapter_manage_flat_share_list_view, null);

            loadScreenElements(convertView);

            number.setText(position + 1 + ". ");
            name.setText(list.get(position).getFirstName());

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
                FragmentManageFlatShareController.removeItem(position);
            }
        });

    }

    private void loadScreenElements(View convertView) {

        name = convertView.findViewById(R.id.nameManageFlatShare);
        number = convertView.findViewById(R.id.numberManageFlatShare);
        remove = convertView.findViewById(R.id.removeManageFlatShare);

    }

}