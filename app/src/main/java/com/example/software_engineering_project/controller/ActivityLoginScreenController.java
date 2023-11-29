package com.example.software_engineering_project.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.software_engineering_project.R;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.dataservice.UserService;
import com.example.software_engineering_project.entity.User;

import java.util.UUID;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

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
         test();
    }

    private void test() {
        UserService service = RetrofitClient.getInstance().create(UserService.class);

        Observable<User> user = service.getEntity(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        user.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<User>() {
    
                @Override
                public void onNext(@NonNull User user) {
                    loginButton.setText(user.toString());
                }
    
                @Override
                public void onError(Throwable e) {
                    System.out.println("Error while loading user" + e.toString());
                }
    
                @Override
                public void onComplete() {
                    System.out.println("User fetching completed");
                }
    
            });
        
        loginButton.setText("Test");
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