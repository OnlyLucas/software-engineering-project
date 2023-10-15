package com.example.software_engineering_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;



public class LoginScreen extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.addButtons();
    }

    private void addButtons(){

        loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent mainScreen = new Intent(LoginScreen.this, MainScreen.class);
            startActivity(mainScreen);
        });

        registerButton=findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> {
            Intent registerScreen = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(registerScreen);
        });
    }
}