package com.example.software_engineering_project.dataservice;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://your-backend-url.com/api/";

    private final Retrofit retrofit;

    public ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public <T> EntityService<T> getEntityService(Class<T> entityClass) {
        String entityPath = entityClass.getSimpleName().toLowerCase() + "s"; // assuming plural form for simplicity
        return retrofit.create(EntityService.class);
    }
}
