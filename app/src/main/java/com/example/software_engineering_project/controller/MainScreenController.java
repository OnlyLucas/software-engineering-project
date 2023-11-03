package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.software_engineering_project.R;

public class MainScreenController extends AppCompatActivity {

    private Button logOutButton;
    private Button goSettingsButton;
    private Button groceryListButton;
    private Button cleaningPlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        this.addButtons();

    }

    public void callFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }


    private void addButtons(){

        groceryListButton = findViewById(R.id.groceryListButton);
        groceryListButton.setOnClickListener(view -> {
            Fragment fragment = new Fragment(R.layout.fragment_grocery_list_screen);
            callFragment(fragment);
        });

        goSettingsButton = findViewById(R.id.goSettingsButtons);
        goSettingsButton.setOnClickListener(view -> {
            Fragment fragment = new Fragment(R.layout.fragment_setting_screen);
            callFragment(fragment);

        });

        cleaningPlanButton = findViewById(R.id.cleaningPlanButton);
        cleaningPlanButton.setOnClickListener(view -> {
            Fragment fragment = new Fragment(R.layout.fragment_cleaning_plan);
            callFragment(fragment);
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