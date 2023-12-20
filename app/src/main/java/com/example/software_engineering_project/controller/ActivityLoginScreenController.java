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
import com.example.software_engineering_project.repository.AppStateRepository;
import com.example.software_engineering_project.repository.GroupMembershipRepository;
import com.example.software_engineering_project.repository.UserRepository;

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

    /**
     * Initializes the activity and sets up the login screen.
     * - Creates an instance of GroupMembershipRepository for managing group memberships.
     * - Retrieves the application context.
     * - Sets the content view to the login screen layout.
     * - Loads and initializes UI elements required for the login screen.
     * - Assigns click listeners to buttons for login and registration functionalities.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if available.
     */
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
     * Loads and initializes UI elements from the login screen layout.
     * - Retrieves and assigns the login button, register button, email input field, and password input field
     *   required for user authentication and navigation to the registration screen.
     */
    private void loadScreenElements() {

        loginButton = findViewById(R.id.loginButton);
        registerButtonLogin = findViewById(R.id.registerButtonLogin);
        emailInput = findViewById(R.id.enterLoginEmail);
        passwordInput = findViewById(R.id.enterLoginPassword);
    }

    /**
     * Assigns click listeners to the login and register buttons within the login screen.
     * - Sets a click listener for the login button to handle login attempts.
     * - Sets a click listener for the register button to initiate the registration process.
     * The click listeners are associated with their respective onClick methods.
     */
    private void addButtons() {

        loginButton.setOnClickListener(this::onClickLoginButton);
        registerButtonLogin.setOnClickListener(this::startRegisterActivity);

    }

    /**
     * Initiates the registration screen by navigating from the login screen to the registration screen.
     * Starts the ActivityRegisterScreenController upon clicking the registration button.
     *
     * @param view The View object representing the clicked registration button.
     */
    private void startRegisterActivity(View view) {

        Intent registerScreen = new Intent(ActivityLoginScreenController.this, ActivityRegisterScreenController.class);
        startActivity(registerScreen);

    }

    /**
     * Handles the click event of the login button by performing the following actions:
     * - Checks login inputs for email and password validity.
     * - Initiates a login service using Retrofit with provided credentials.
     * - Enqueues a login call to the server and handles its response.
     * - Processes the login response:
     *   - If successful, sets the authenticated user in the AppStateRepository and navigates to the MainActivity.
     *   - If authentication fails, displays appropriate error messages and clears the current user.
     *
     * @param view The View object representing the clicked login button.
     */
    private void onClickLoginButton(View view) {

        if (!checkLoginInputs()) {
            // if inputs are faulty
            return;
        }

        LoginService loginService = RetrofitClient.getInstanceWithCredentials(email, password)
                .create(LoginService.class);
        Call<User> call = loginService.login();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    User authenticatedUser = response.body();

                    // Error in authentication, clear currentAppUSer
                    if (authenticatedUser == null) {
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
                    if (response.code() == 401) {
                        Log.e(TAG, "Bad credentials for login");
                        ToastUtil.makeToast(context.getString(R.string.error_with_authentication_login_again), context);
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

    /**
     * Checks and validates the user login inputs for email and password fields.
     * - Retrieves email and password from respective input fields.
     * - Validates if the email and password fields are empty.
     * - Sets up a dummy user for authentication that the AuthInterceptor uses.
     * - Sets the current user in the AppStateRepository.
     *
     * @return Returns true if both email and password fields are non-empty, otherwise displays respective error messages and returns false.
     */
    private boolean checkLoginInputs() {

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