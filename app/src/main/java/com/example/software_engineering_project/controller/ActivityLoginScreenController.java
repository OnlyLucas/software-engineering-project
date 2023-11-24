package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.UserApiClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.User;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ActivityLoginScreenController extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private UserService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Test 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.addButtons();

//        // Initialize Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:80/v1/") // Replace with your API base URL
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//
//        // Create an instance of the API service
//        apiService = retrofit.create(UserService.class);
//
//        // Make the sample request
//        makeSampleRequest();
//        //TODO delete
//         test();
    }

//    private void makeSampleRequest() {
//        // Example: Make a request to get user data
//        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
//        Call<User> userCall = apiService.getUser(userId);
//        userCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    User user = response.body();
//                    // Process the user data
//                } else {
//                    // Handle the error
//                    Log.e("SampleRequest", "Failed to get user. Error code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                // Handle the failure
//                Log.e("SampleRequest", "Request failed: " + t.getMessage(), t);
//            }
//        });
//    }

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