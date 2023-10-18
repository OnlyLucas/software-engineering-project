package com.example.software_engineering_project;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ManageFlatShareMain extends AppCompatActivity {
    static ListView listView;
    EditText input;
    ImageView enter;
    static ManageFlatShareViewAdapter adapter;
    static ArrayList<String> items;
    static Context context;
    private Button goBackButtonManageFlatShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flat_share_main);

        this.addButtons();

        listView = findViewById(R.id.list);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        context = getApplicationContext();

        // add hardcoded items to list
        items = new ArrayList<>();

        //Auslesebefehl für Datenbank
        items.add("Jonas");
        items.add("Nikos");
        items.add("Lucas");
        items.add("Meike");
        items.add("Laura");

        listView.setLongClickable(true);
        adapter = new ManageFlatShareViewAdapter(this, items);
        listView.setAdapter(adapter);

        // Display the person's name when the person's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(ManageFlatShareMain.this, clickedItem, Toast.LENGTH_SHORT).show();
            }
        });



        // add person when the user presses the add button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if (text.length() == 0) {
                    makeToast("Enter an e-mail.");
                } else {
                    addItem(text);
                    input.setText("");
                    makeToast("Added " + text);
                    loadContent();
                }
            }
        });

    }

    // function to read flat share member list from file and load it into ListView
    public void loadContent() {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "list.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            // [Jonas, Nikos, Lucas, Meike, Laura]
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            // There may be no items in the grocery list.
            if (split.length == 1 && split[0].isEmpty())
                items = new ArrayList<>();
            else items = new ArrayList<>(Arrays.asList(split));

            adapter = new ManageFlatShareViewAdapter(this, items);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        goBackButtonManageFlatShare = findViewById(R.id.goBackButtonManageFlatShare);
        goBackButtonManageFlatShare.setOnClickListener(view -> {
            Intent SettingScreen = new Intent(ManageFlatShareMain.this, SettingScreen.class);
            startActivity(SettingScreen);
        });
    }
}