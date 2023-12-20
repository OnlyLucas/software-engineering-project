package com.example.software_engineering_project.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.User;

import request.UserWithPasswordRequest;

import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ActivityRegisterScreenController handles the user registration screen in the FlatFusion app.
 * It allows users to input their registration details, including first name, last name, email, and password.
 * The class performs input validation and communicates with the UserRepository to store new user data.
 */
public class ActivityRegisterScreenController extends AppCompatActivity {

    private Button cancelButtonRegister, registerButtonRegister;
    private Context context;
    private EditText confirmPasswordRegister, emailRegister, firstNameRegister, passwordRegister, usernameRegister, surnameRegister;
    private static UserRepository userRepository;

    /**
     * Initializes the activity upon its creation for user registration.
     * - Retrieves the application context.
     * - Creates an instance of UserRepository for user-related operations.
     * - Calls the superclass method for initialization.
     * - Sets the content view to the user registration screen layout.
     * - Loads and initializes UI elements required for the registration screen.
     * - Assigns click listeners to buttons for user registration functionalities.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = getApplicationContext();
        userRepository = new UserRepository(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        loadScreenElements();
        addButtons();

    }

    /**
     * Sets click listeners to buttons on the user registration screen for specific actions.
     * - Assigns a click listener to the cancel button to navigate back to the login screen.
     * - Assigns a click listener to the register button to trigger user registration after input validation.
     *   - If input validation is successful, initiates the registration process and navigates to the login screen.
     *   - Otherwise, prevents registration if the input validation fails.
     */
    private void addButtons() {

        cancelButtonRegister.setOnClickListener(view -> {
            Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
            startActivity(loginScreen);
        });

        //Todo save new user data
        registerButtonRegister.setOnClickListener(view -> {
            if (checkInputsAndRegister()) {
                Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
                startActivity(loginScreen);
            }
        });

    }

    /**
     * Validates the user input fields for user registration and initiates the registration process if inputs are valid.
     * - Retrieves data from various input fields such as first name, last name, email, password, confirm password, and username.
     * - Validates the input fields based on specific criteria:
     *    - Checks if the first name contains only letters and is within a reasonable length.
     *    - Checks if the last name contains only letters and is within a reasonable length.
     *    - Ensures the username is not empty and does not exceed a certain length.
     *    - Validates the email address for a standard email format and length.
     *    - Checks if the password and confirm password match, are within specified length limits, and are not too short.
     * - Creates a new User object with the validated user information.
     * - Initiates the registration process by creating a UserWithPasswordRequest object and adding it to the system.
     *
     * @return boolean Returns true if all input fields pass the validation criteria and the registration process is initiated; otherwise, returns false.
     */
    private boolean checkInputsAndRegister() {

        String firstName = firstNameRegister.getText().toString();
        String lastName = surnameRegister.getText().toString();
        String email = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();
        String confirmPassword = confirmPasswordRegister.getText().toString();
        String username = usernameRegister.getText().toString();

        // Check if firstName contains only letters
        if (!isValidName(firstName) || firstName.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_first_name), context);
            return false;
        }

        if (firstName.length() > 20) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_first_name), context);
            return false;
        }

        // Check if lastName contains only letters
        if (!isValidName(lastName) || lastName.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_last_name), context);
            return false;
        }

        if (lastName.length() > 20) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_last_name), context);
            return false;
        }

        if (username.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_username), context);
            return false;
        }

        if (username.length() > 15) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_username), context);
            return false;
        }

        // Check if eMail has a standard email format
        if (!isValidEmail(email) || email.length() > 30) {
            ToastUtil.makeToast(getString(R.string.enter_valid_mail), context);
            return false;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtil.makeToast(getString(R.string.passwords_not_matching), context);
            return false;
        }

        if (password.length() == 0 || confirmPassword.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_password), context);
            return false;
        }

        if (password.length() > 25 || confirmPassword.length() > 25) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_password), context);
            return false;
        }

        if (password.length() < 9 || confirmPassword.length() < 9) {
            ToastUtil.makeToast(getString(R.string.enter_longer_password), context);
            return false;
        }

        User newUser = new User(email, username, firstName, lastName);
        newUser.setPassword(password);

        UserWithPasswordRequest user = new UserWithPasswordRequest(newUser, password);
        addItem(user);

        return true;
    }

    /**
     * Adds a new user with password information to the system.
     *
     * @param user A UserWithPasswordRequest object containing user details along with the password for registration.
     *             It includes the user's email, username, first name, last name, and password.
     *             This method adds the user to the system by utilizing the UserRepository to perform insertion.
     */
    private void addItem(UserWithPasswordRequest user) {

        userRepository.insertUser(user, context);

    }

    // Helper method to check if a name contains only letters
    /**
     * Checks if the provided name contains only letters using regular expressions.
     *
     * @param name The name string to be validated.
     * @return boolean Returns true if the name contains only letters; otherwise, returns false.
     *         - Uses a regular expression pattern to match the name against the sequence of alphabetic characters.
     *         - Returns true if the name consists solely of alphabetic characters; otherwise, returns false.
     */
    private boolean isValidName(String name) {

        // Use regex to check if the name contains only letters
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();

    }

    // Helper method to check if an email has a standard format
    /**
     * Checks if the provided email string conforms to a basic email format using regular expressions.
     *
     * @param email The email string to be validated.
     * @return boolean Returns true if the email string matches a basic email format; otherwise, returns false.
     *         - Uses a regular expression pattern to match the email against a basic format structure:
     *             - The email pattern includes alphanumeric characters, dots, underscores, percentage signs, plus, and hyphens
     *               for the local part, followed by the '@' symbol and the domain part, ending with a valid domain extension.
     *         - Returns true if the email string adheres to the basic email format; otherwise, returns false.
     */
    private boolean isValidEmail(String email) {

        // Use a simple regex for basic email format validation
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    /**
     * Initializes the UI elements by finding and assigning the corresponding views from the XML layout.
     * This method fetches specific views related to user registration form elements.
     * - Assigns views for email, first name, last name, username, password, confirm password, and buttons.
     */
    private void loadScreenElements() {

        cancelButtonRegister = findViewById(R.id.cancelButtonRegister);
        confirmPasswordRegister = findViewById(R.id.confirmPasswordRegister);
        emailRegister = findViewById(R.id.emailRegister);
        firstNameRegister = findViewById(R.id.firstNameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        registerButtonRegister = findViewById(R.id.registerButtonRegister);
        usernameRegister = findViewById(R.id.usernameRegister);
        surnameRegister = findViewById(R.id.surnameRegister);

    }

}