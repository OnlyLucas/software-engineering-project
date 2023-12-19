package com.example.software_engineering_project.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.LoginService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.util.UILoaderUtil;
import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.example.software_engineering_project.viewmodel.GroupMembershipRepository;
import com.example.software_engineering_project.viewmodel.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ActivityLoginScreenController handles the user interface for the login screen.
 * It provides functionality for user login and navigation to the registration screen.
 */
public class ActivityLoginScreenController extends AppCompatActivity {
    private static final String TAG = ActivityLoginScreenController.class.getSimpleName();
    private Button loginButton, registerButtonLogin;
    private static EditText emailInput, passwordInput;
    private UserRepository userRepository;
    private Context context;
    private String email, password;
    private GroupMembershipRepository groupMembershipRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        groupMembershipRepository = new GroupMembershipRepository();
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

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

        LoginService loginService = RetrofitClient.getLoginInstance(email, password)
                                        .create(LoginService.class);
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
                        AppStateRepository.setCurrentGroup(null);

                        Log.e(TAG, "Login not successful");
                        // show toast of login failure
                        ToastUtil.makeToast(context.getString(R.string.login_not_successful), context);

                        return;
                    }

                    Log.i(TAG, "Login successful");
                    // show toast of success
                    ToastUtil.makeToast(context.getString(R.string.login_successful), context);

                    // set password as it is not transmitted by the backend and not yet in the user entity.
                    authenticatedUser.setPassword(password);

                    // Now set the user with all their attributes
                    AppStateRepository.setCurrentUser(authenticatedUser);
                    groupMembershipRepository.getGroupByUserId(authenticatedUser.getId(), context);
                    // TODO move currentGroup from UserRepository to AppStateRepository

                    // Route to MainActivity
                    Intent mainScreen = new Intent(ActivityLoginScreenController.this, ActivityMainScreenController.class);
                    startActivity(mainScreen);
                } else {
                    // Reset current user
                    if(response.code() == 401){
                        Log.e(TAG, "Bad credentials. Rerouting to login activity.");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
                        UILoaderUtil.startLoginActivity(context);
                        return;
                    }

                    Log.e(TAG, "Error during login");
                    ToastUtil.makeToast(context.getString(R.string.error_during_login), context);

                    // Clear current app user
                    AppStateRepository.setCurrentUser(null);
                    AppStateRepository.setCurrentGroup(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Network error during login: " + t);
                ToastUtil.makeToast(context.getString(R.string.error_during_login), context);
            }
        });
    }

    private boolean checkLoginInputs() {

        // get the inputs
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if (email.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_email), context);
            return false;
        } else if (password.length() == 0) {
            ToastUtil.makeToast(context.getString(R.string.enter_password), context);
            return false;
        } else {

            // Create Dummy User for Authentication that the AuthInterceptor uses
            User authUser = new User();
            authUser.setEmail(email);
            //TODO remove or delete
            System.out.println("Set password to current user: " + password);
            authUser.setPassword(password);
            AppStateRepository.setCurrentUser(authUser);

            return true;
        }
    }
}