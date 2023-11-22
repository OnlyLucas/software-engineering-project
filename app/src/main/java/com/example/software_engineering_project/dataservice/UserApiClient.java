package com.example.software_engineering_project.dataservice;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class UserApiClient {
    private static final String BASE_URL = "http://localhost:1337/v1/";

    private final Retrofit retrofit;

    public UserApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }
}
