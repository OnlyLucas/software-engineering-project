package com.example.software_engineering_project.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.software_engineering_project.R;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.util.PasswordHashUtil;
import com.example.software_engineering_project.util.ToastUtil;
import com.example.software_engineering_project.viewmodel.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityRegisterScreenController extends AppCompatActivity {

    private Button cancelButtonRegister, registerButtonRegister;
    private Context context;
    private EditText emailRegister, firstNameRegister, passwordRegister, surnameRegister;
    private static UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userRepository = new UserRepository();
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

    private void checkInputs() {
        String firstName = firstNameRegister.getText().toString();
        String lastName = surnameRegister.getText().toString();
        String eMail = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();

        // Check if firstName contains only letters
        if (!isValidName(firstName)) {
            ToastUtil.makeToast("Please enter valid first name", context);
            return; // Stop further processing
        }

        // Check if lastName contains only letters
        if (!isValidName(lastName)) {
            ToastUtil.makeToast("Please enter valid last name", context);
            return; // Stop further processing
        }

        // Check if eMail has a standard email format
        if (!isValidEmail(eMail)) {
            ToastUtil.makeToast("Please enter valid email", context);
            return; // Stop further processing
        }

        // Hash the password
        String hashedPassword = PasswordHashUtil.hashPassword(password);

        // Check if hashing was successful
        if (hashedPassword != null) {
            // TODO create new User
            User user = new User(firstName, lastName, eMail, hashedPassword);
            addItem(user);

            ToastUtil.makeToast(getString(R.string.please_login_with_your_created_credentials), context);
        } else {
            // Handle the case where hashing failed
            ToastUtil.makeToast("Error hashing password. Please try again.", context);
        }

    }

    private void addItem(User user) {
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
        emailRegister = findViewById(R.id.emailRegister);
        firstNameRegister = findViewById(R.id.firstNameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        registerButtonRegister = findViewById(R.id.registerButtonRegister);
        surnameRegister = findViewById(R.id.surnameRegister);

    }

}