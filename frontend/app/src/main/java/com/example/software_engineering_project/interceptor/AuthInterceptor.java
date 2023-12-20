package com.example.software_engineering_project.interceptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.software_engineering_project.entity.User;
import com.example.software_engineering_project.repository.AppStateRepository;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/***
 * This interceptor tries to add authorization with a Basic header to each http request made.
 */
public class AuthInterceptor implements Interceptor {

    private final String TAG = "authorization_interceptor";

    /**
     * Intercepts the outgoing request and adds Authorization header based on the user credentials.
     *
     * @param chain The interceptor chain.
     * @return The response after processing the intercepted request.
     * @throws IOException If an I/O error occurs during the processing of the request.
     */
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request originalRequest = chain.request();

        User user = AppStateRepository.getCurrentAppUserLiveData().getValue();

        if (user == null) {
            //TODO remove or log
            Log.d(TAG, "AppUser is null. Continue with request.");
            return chain.proceed(originalRequest);
        }

        try{
            String username = user.getEmail();
            String password = user.getPassword();
            String credentials = Credentials.basic(username, password);

            Log.d(TAG, "Authorization Header created for request: " + chain.request());
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", credentials)
                    .build();
            return chain.proceed(newRequest);
        } catch (NullPointerException e) {
            Log.d(TAG, "User credentials for authorization not available.", e);
            // Go on with the call
            // If auth not required for call, it will still be successful
        }

        return chain.proceed(originalRequest);
    }
}
