package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.example.software_engineering_project.R;

public abstract class ListScreenController<T> extends AppCompatActivity {

    static ListView listView;
    EditText input;
    ImageView enter;
    static ArrayAdapter<String> adapter;
    ArrayList<T> items;
    static Context context;
    private Button goBackButtonGroceryList;
    private final int activity;

    /**
     * @param activity is the activity for a controller of a certain screen
     */
    public ListScreenController(int activity, ArrayAdapter<String> adapter){

        this.activity = activity;
        ListScreenController.adapter = adapter;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity);

        this.addButtons();

        listView = findViewById(R.id.groceryList);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.enter);
        context = getApplicationContext();

        // add hardcoded items to grocery list
        items = this.loadItems();

        listView.setLongClickable(true);
        adapter.addAll(items.toString());
        listView.setAdapter(adapter);

        // Display the item name when the item's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(ListScreenController.this, clickedItem, Toast.LENGTH_SHORT).show();
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

        // add item when the user presses the enter button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = input.getText().toString();
                if (text == null || text.length() == 0) {
                    makeToast(getString(R.string.enter_an_item));
                } else {
                    // either abstract method createObjectFromString or method in method in model class
                    //addItem();
                    input.setText("");
                    makeToast(getString(R.string.added) + text);
                }

            }

        });

    }

    // Override onDestroy() to save the contents of the grocery list right before the app is terminated
    @Override
    protected void onDestroy() {

        File path = getApplicationContext().getFilesDir();
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
    public void removeItem(int i) {

        makeToast(getString(R.string.removed) + items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);

    }

    public void uncheckItem(int i){
        makeToast(getString(R.string.unchecked) + items.get(i));
    }

    // function to add an item given its name.
    public void addItem(T item) {

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

    public abstract void addButtons();

    public abstract ArrayList<T> loadItems();

}
