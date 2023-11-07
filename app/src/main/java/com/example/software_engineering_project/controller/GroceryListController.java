package com.example.software_engineering_project.controller;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.GroceryListListViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GroceryListController extends AppCompatActivity {

    static ListView listView;
    EditText input;
    ImageView enter;
    static GroceryListListViewAdapter adapter;
    static ArrayList<String> items;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grocery_list_controller);

        listView = findViewById(R.id.list);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.enter);
        context = getApplicationContext();

        // add hardcoded items to grocery list
        items = new ArrayList<>();

        //Auslesebefehl für Datenbank
        items.add("Apple");
        items.add("Banana");
        items.add("Orange");
        items.add("Strawberry");
        items.add("Kiwi");

        listView.setLongClickable(true);
        adapter = new GroceryListListViewAdapter(this, items);
        listView.setAdapter(adapter);

        // Display the item name when the item's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(GroceryListController.this, clickedItem, Toast.LENGTH_SHORT).show();
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
                    makeToast("Enter an item.");
                } else {
                    addItem(text);
                    input.setText("");
                    makeToast("Added " + text);
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