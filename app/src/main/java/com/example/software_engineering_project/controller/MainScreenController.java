package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import com.example.software_engineering_project.R;

public class MainScreenController extends AppCompatActivity {

    private Button logOutButton;
    private Button goSettingsButton;
    private Button groceryListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        this.addButtons();
        this.setWelcomeMessage();

    }

    private void setWelcomeMessage(){
        String name = "Meike";
        TextView textView = findViewById(R.id.text_view_name);
        textView.setText(name);
    }

    private void addButtons(){
        logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(view -> {
            Intent LoginScreen = new Intent(MainScreenController.this, LoginScreenController.class);
            startActivity(LoginScreen);
        });

        groceryListButton = findViewById(R.id.groceryListButton);
        groceryListButton.setOnClickListener(view -> {
            Intent GroceryListMain = new Intent(MainScreenController.this, GroceryListController.class);
            startActivity(GroceryListMain);
        });

        goSettingsButton = findViewById(R.id.goSettingsButtons);
        goSettingsButton.setOnClickListener(view -> {
            Intent SettingScreen = new Intent(MainScreenController.this, SettingScreenController.class);
            startActivity(SettingScreen);
        });


    }
}


/** Just to save the old version
 * cancelButton=findViewById(R.id.cancelButton);
 *         cancelButton.setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View view) {
 *                 Intent loginScreen = new Intent(RegisterScreen.this, LoginScreen.class);
 *                 startActivity(loginScreen);
 *             }
 *         });
 */