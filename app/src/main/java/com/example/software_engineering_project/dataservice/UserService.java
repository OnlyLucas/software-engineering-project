package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.User;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") UUID userId);

    @POST("users")
    Call<User> createUser(@Body User user);

    // Add other API endpoints as needed
}
