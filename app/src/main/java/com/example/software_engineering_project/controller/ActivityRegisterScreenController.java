package com.example.software_engineering_project.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.UserCreate;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userRepository = new UserRepository(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        context = getApplicationContext();
        loadScreenElements();
        addButtons();

    }

    private void addButtons() {

        cancelButtonRegister.setOnClickListener(view -> {
            Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
            startActivity(loginScreen);
        });

        //Todo save new user data
        registerButtonRegister.setOnClickListener(view -> {
            Intent loginScreen = new Intent(ActivityRegisterScreenController.this, ActivityLoginScreenController.class);
            startActivity(loginScreen);
            checkInputs();
        });

    }

    /**
     * Checks the user inputs for validity and creates a new user if the inputs are valid.
     * Displays appropriate toast messages for invalid inputs.
     */
    private void checkInputs() {
        String firstName = firstNameRegister.getText().toString();
        String lastName = surnameRegister.getText().toString();
        String eMail = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();
        String confirmPassword = confirmPasswordRegister.getText().toString();
        String username = usernameRegister.getText().toString();

        // Check if firstName contains only letters
        if (!isValidName(firstName) || firstName.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_first_name), context);
            return;
        }

        if (firstName.length() > 20) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_first_name), context);
            return;
        }

        // Check if lastName contains only letters
        if (!isValidName(lastName) || lastName.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_last_name), context);
            return;
        }

        if (lastName.length() > 20) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_last_name), context);
            return;
        }

        if (username.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_valid_username), context);
            return;
        }

        if (username.length() > 15) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_username), context);
            return;
        }

        // Check if eMail has a standard email format
        if (!isValidEmail(eMail) || eMail.length() > 30) {
            ToastUtil.makeToast(getString(R.string.enter_valid_mail), context);
            return;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtil.makeToast(getString(R.string.passwords_not_matching), context);
            return;
        }

        if (password.length() == 0 || confirmPassword.length() == 0) {
            ToastUtil.makeToast(getString(R.string.enter_password), context);
            return;
        }

        if (password.length() > 25 || confirmPassword.length() > 25) {
            ToastUtil.makeToast(getString(R.string.enter_shorter_password), context);
            return;
        }

        // TODO create new User
        UserCreate user = new UserCreate(firstName, lastName, username, eMail, password);
        addItem(user);

        ToastUtil.makeToast(getString(R.string.please_login_with_your_created_credentials), context);

    }

    private void addItem(UserCreate user) {
        userRepository.insertUser(user, context);
    }

    // Helper method to check if a name contains only letters
    private boolean isValidName(String name) {
        // Use regex to check if the name contains only letters
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // Helper method to check if an email has a standard format
    private boolean isValidEmail(String email) {
        // Use a simple regex for basic email format validation
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

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