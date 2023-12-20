package com.example.software_engineering_project.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * LoginInterceptor is an OkHttp Interceptor designed for adding Authorization headers
 * with login credentials to outgoing requests. It allows dynamically setting the
 * username and password for each request.
 */
public class LoginInterceptor implements Interceptor {

    private String username;
    private String password;

    /**
     * Constructs a new LoginInterceptor with the specified username and password.
     *
     * @param username The username for authentication.
     * @param password The password for authentication.
     */
    public LoginInterceptor(String username, String password) {

        this.username = username;
        this.password = password;

    }

    /**
     * Intercepts the outgoing request and adds Authorization header based on the provided credentials.
     *
     * @param chain The interceptor chain.
     * @return The response after processing the intercepted request.
     * @throws IOException If an I/O error occurs during the processing of the request.
     */
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request originalRequest = chain.request();

        String credentials = Credentials.basic(username, password);

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", credentials)
                .build();

        return chain.proceed(newRequest);
    }
}
