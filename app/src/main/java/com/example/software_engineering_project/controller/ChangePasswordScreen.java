package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.software_engineering_project.R;

public class ChangePasswordScreen extends AppCompatActivity {

    private Button goBackButton;
    private Button saveChangesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        this.addButtons();
    }

    private void addButtons(){
        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(view -> {
            Intent SettingScreen = new Intent(ChangePasswordScreen.this, com.example.software_engineering_project.controller.SettingScreen.class);
            startActivity(SettingScreen);
        });

        saveChangesButton = findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(view -> {

            new AlertDialog.Builder(ChangePasswordScreen.this)
                    .setTitle(R.string.change_password)
                    .setMessage(R.string.are_you_sure_you_want_to_change_your_password)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent SettingScreen = new Intent(ChangePasswordScreen.this,SettingScreen.class);
                            startActivity(SettingScreen);
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
}