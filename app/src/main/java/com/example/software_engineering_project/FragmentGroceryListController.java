package com.example.software_engineering_project;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.software_engineering_project.adapter.GroceryListListViewAdapter;
import com.example.software_engineering_project.controller.GroceryListController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGroceryListController #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGroceryListController extends Fragment {

    View fragmentView;
    static ListView listView;
    EditText input;
    ImageView enter;
    static ArrayList<String>  items = new ArrayList<>();
    static ArrayAdapter<String> adapter;

    static Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //adapter = new ArrayAdapter<String> (getActivity().getApplicationContext(), R.layout.grocery_list_list_view_adapter, R.id.name ,items);
        adapter = new GroceryListListViewAdapter(getActivity(),items);
        items.add("Gerhard");
        items.add("Hans");
        items.add("Meike");
        items.add("Lucas");
        items.add("Laura");
        items.add("Nikos");
        items.add("Jonas");
        items.add("Dieter");


        //HERE IS SOMETHING WRONG

        //listView = fragmentView.findViewById(R.id.list);
        //input = input.findViewById(R.id.input);
        //enter = fragmentView.findViewById(R.id.enter);

        fragmentView = inflater.inflate(R.layout.fragment_grocery_list_controller, container, false);
        listView = fragmentView.findViewById(R.id.list);
        context = getActivity();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity().getApplicationContext(), R.layout.grocery_list_list_view_adapter, R.id.name ,items);
        listView.setLongClickable(true);
        //ArrayAdapter<String> adapter = new GroceryListListViewAdapter(getActivity(), items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(), clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        // Remove an item when its row is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItem(i);
                return false;
            }
        });

        //HERE IS SOMETHING WRONG
        /**
        enter.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Test");
            }
        });
         */

        return fragmentView;
    }




    // Override onDestroy() to save the contents of the grocery list right before the app is terminated
    @Override
    public void onDestroy() {
        File path = context.getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {
        makeToast("Removed: " + items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);
    }

    public static void uncheckItem(int i) {
        makeToast("Unchecked: " + items.get(i));
    }

    // function to add an item given its name.
    public static void addItem(String item) {
        items.add(item);
        listView.setAdapter(adapter);
    }

    // function to make a Toast given a string
    static Toast t;

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }

}