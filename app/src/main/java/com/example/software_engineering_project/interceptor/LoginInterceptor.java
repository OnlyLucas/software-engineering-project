package com.example.software_engineering_project.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoginInterceptor implements Interceptor {

    private String username;
    private String password;

    public LoginInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String credentials = Credentials.basic(username, password);

        //TODO remove or log
        System.out.println("Authorization header: '" + credentials + "'" );

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", credentials)
                .build();

        return chain.proceed(newRequest);
    }
}
