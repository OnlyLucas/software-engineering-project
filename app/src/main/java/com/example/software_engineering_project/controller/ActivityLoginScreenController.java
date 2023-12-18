package com.example.software_engineering_project.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.LoginService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ActivityLoginScreenController handles the user interface for the login screen.
 * It provides functionality for user login and navigation to the registration screen.
 */
public class ActivityLoginScreenController extends AppCompatActivity {

    private Button loginButton, registerButtonLogin;
    private static EditText emailInput, passwordInput;
    private LoginService loginService;
    private UserRepository userRepository;
    private Context context;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        loginService = RetrofitClient.getInstance().create(LoginService.class);
        setContentView(R.layout.activity_login_screen);
        loadScreenElements();
        addButtons();

    }

    /**
     * Loads elements from the XML layout file.
     */
    private void loadScreenElements() {

        loginButton = findViewById(R.id.loginButton);
        registerButtonLogin = findViewById(R.id.registerButtonLogin);
        emailInput = findViewById(R.id.enterLoginEmail);
        passwordInput = findViewById(R.id.enterLoginPassword);
    }

    private void addButtons() {

        loginButton.setOnClickListener(this::onClickLoginButton);
        registerButtonLogin.setOnClickListener(this::startRegisterActivity);
    }

    private void startRegisterActivity(View view){
        Intent registerScreen = new Intent(ActivityLoginScreenController.this, ActivityRegisterScreenController.class);
        startActivity(registerScreen);
    }

    private void onClickLoginButton(View view){
        if(!checkLoginInputs()){
            // if inputs are faulty
            return;
        }

        Call<User> call = loginService.login();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    User authenticatedUser = response.body();

                    // Error in authentication, clear currentAppUSer
                    if(authenticatedUser == null){
                        // Clear credentials
                        AppStateRepository.setCurrentUser(null);

                        System.out.println("Login not successful");
                        // show toast of login failure
                        ToastUtil.makeToast("Login not successful", context);

                        return;
                    }

                    // TODO add text outsourcing
                    System.out.println("Login successful");
                    // show toast of success
                    ToastUtil.makeToast("Login successful", context);

                    // set password as it is not transmitted by the backend and not yet in the user entity.
                    authenticatedUser.setPassword(password);

                    // Now set the user with all their attributes
                    AppStateRepository.setCurrentUser(authenticatedUser);

                    // TODO set current group
                    // TODO move currentGroup from UserRepository to AppStateRepository

                    System.out.println("Login successful");
                    ToastUtil.makeToast("Login successful.", context);

                    // Route to MainActivity
                    Intent mainScreen = new Intent(ActivityLoginScreenController.this, ActivityMainScreenController.class);
                    startActivity(mainScreen);
                } else {
                    // Reset current user
                    if(response.code() == 401){
                        System.out.println("Bad credentials used for login");
                        ToastUtil.makeToast("Username or password not correct", context);
                        return;
                    }

                    System.out.println("Error during login");
                    ToastUtil.makeToast("Error during login", context);

                    // Clear current app user
                    AppStateRepository.setCurrentUser(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Error during login: " + t);
                ToastUtil.makeToast("Error during login. Try again", context);
                // Handle the failure if needed
                // For example, show an error message
            }
    }

    private boolean checkLoginInputs() {

        // get the inputs
        String email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        //TODO outsource string
        if (email.length() == 0) {
            ToastUtil.makeToast("Enter email", context);
            return false;
        } else if (password.length() == 0) {
            ToastUtil.makeToast("Enter password", context);
            return false;
        } else {

            // Create Dummy User for Authentication that the AuthInterceptor uses
            User authUser = new User();
            authUser.setEmail(email);
            authUser.setPassword(password);
            AppStateRepository.postCurrentUser(authUser);

            try{
                wait(1000);
            } catch (Exception e){

            }

            // Request: Authorization Header: Basic username:password

            return true;
        }
    }
}