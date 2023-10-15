package com.example.software_engineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    private Button logOutButton;

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
            Intent LoginScreen = new Intent(MainScreen.this,LoginScreen.class);
            startActivity(LoginScreen);
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