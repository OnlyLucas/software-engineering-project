package com.example.software_engineering_project.interceptor;

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

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request originalRequest = chain.request();

        User user = AppStateRepository.getCurrentAppUserLiveData().getValue();

        if (user == null) {
            //TODO remove or log
            System.out.println("AppUser is null. Continue with request.");
            return chain.proceed(originalRequest);
        }

        try {
            String username = user.getEmail();
            String password = user.getPassword();
            String credentials = Credentials.basic(username, password);
            //TODO remove or log
            System.out.println("Authorization header: '" + credentials + "'");

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
