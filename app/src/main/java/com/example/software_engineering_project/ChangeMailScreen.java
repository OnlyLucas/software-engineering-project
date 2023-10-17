package com.example.software_engineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ChangeMailScreen extends AppCompatActivity {

    private Button goBackButtonMail;
    private Button saveChangesButtonMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mail_screen);
        this.addButtons();
    }

    private void addButtons(){
        goBackButtonMail = findViewById(R.id.goBackButtonMail);
        goBackButtonMail.setOnClickListener(view -> {
            Intent SettingScreen = new Intent(ChangeMailScreen.this,SettingScreen.class);
            startActivity(SettingScreen);
        });

        saveChangesButtonMail = findViewById(R.id.saveChangesButtonMail);
        saveChangesButtonMail.setOnClickListener(view -> {

            new AlertDialog.Builder(ChangeMailScreen.this)
                    .setTitle(R.string.change_e_mail)
                    .setMessage(R.string.are_you_sure_you_want_to_change_your_mail)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent SettingScreen = new Intent(ChangeMailScreen.this,SettingScreen.class);
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