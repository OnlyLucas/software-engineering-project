package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.UserApiClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.User;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;


public class ActivityLoginScreenController extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.addButtons();

        //TODO delete
         test();
    }

    private void test(){
        {
            UserApiClient apiClient = new UserApiClient();
            UserService userService = apiClient.getUserService();

            // Example: Get user by ID
            UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
            Call<User> userCall = userService.getUser(userId);

            try {
                Response<User> userResponse = userCall.execute();
                if (userResponse.isSuccessful()) {
                    User user = userResponse.body();
                    System.out.println("User: " + user);
                } else {
                    System.err.println("Failed to get user. Error code: " + userResponse.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Example: Create a new user
            User newUser = new User();
            // Set user properties...
            Call<User> createUserCall = userService.createUser(newUser);

            try {
                Response<User> createUserResponse = createUserCall.execute();
                if (createUserResponse.isSuccessful()) {
                    User createdUser = createUserResponse.body();
                    System.out.println("Created User: " + createdUser);
                } else {
                    System.err.println("Failed to create user. Error code: " + createUserResponse.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addButtons(){

        loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent mainScreen = new Intent(ActivityLoginScreenController.this, ActivityMainScreenController.class);
            startActivity(mainScreen);
        });

        registerButton=findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> {
            Intent registerScreen = new Intent(ActivityLoginScreenController.this, ActivityRegisterScreenController.class);
            startActivity(registerScreen);
        });

    }

}