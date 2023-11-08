package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.adapter.CreateFlatShareListViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CreateFlatShareScreenController extends AppCompatActivity {
    static ListView listView;
    EditText input_name, input_mail;
    ImageView enter;
    static CreateFlatShareListViewAdapter adapter;
    static ArrayList<String> items;
    static Context context;
    private Button goBackButtonCreateFlatShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flat_share_screen);

        this.addButtons();

        listView = findViewById(R.id.groceryList);
        input_name = findViewById(R.id.input_name);
        input_mail = findViewById(R.id.input_mail);
        enter = findViewById(R.id.add);
        context = getApplicationContext();

        // add hardcoded items to list
        items = new ArrayList<>();

        listView.setLongClickable(true);
        adapter = new CreateFlatShareListViewAdapter(this, items);
        listView.setAdapter(adapter);

        // Display the person's name when the person's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(CreateFlatShareScreenController.this, clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        // add person when the user presses the add button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input_mail.getText().toString();
                if (text.length() == 0) {
                    makeToast("Enter an e-mail.");
                } else {
                    addItem(text);
                    input_mail.setText("");
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

    // function to remove a member given its index in the list.
    public static void removeItem(int i) {
        makeToast("Removed: " + items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);
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


    private void addButtons() {
        goBackButtonCreateFlatShare = findViewById(R.id.goBackButtonCreateFlatShare);
        goBackButtonCreateFlatShare.setOnClickListener(view -> {
            Intent SettingScreen = new Intent(CreateFlatShareScreenController.this, FragmentSettingsController.class);
            startActivity(SettingScreen);
        });
    }
}