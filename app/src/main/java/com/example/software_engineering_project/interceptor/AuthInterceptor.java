package com.example.software_engineering_project.interceptor;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.viewmodel.AppStateRepository;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/***
 * This interceptor tries to add authorization with a Basic header to each http request made.
 */
public class AuthInterceptor implements Interceptor {
    private Context context;

//    public AuthInterceptor(Context context) {
//        this.context = context;
//    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        User user = AppStateRepository.getCurrentUserLiveData().getValue();

        if (user == null){
            System.out.println("AppUser is null. Continue with request.");
            return chain.proceed(originalRequest);
        }

        String username = user.getEmail();
        String password = user.getPassword();
        String credentials = Credentials.basic(username, password);

        System.out.println("Authorization header: '" + credentials + "'" );

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", credentials)
                .build();

        //make http call
        Response response = chain.proceed(newRequest);

        System.out.println("Authorization Interceptor called");

        // If user is unauthorized (user credentials can't be found in backend) go back to Login Screen and re-login.
        if(response.code() == 401) { // Unauthorized
            // Log user out
            AppStateRepository.setCurrentUser(null);

            Intent i = new Intent(context.getApplicationContext(), ActivityLoginScreenController.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

        return response;
    }
}
