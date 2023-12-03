package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.User;

import java.util.Map;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService{
    @GET("users/{id}")
    Observable<User> getEntity(@Path("id") UUID userId);

    @POST("users")
    Observable<User> createEntity(@Body User user);

    @PATCH("users/{id}")
    Observable<User> partialUpdateEntity(@Path("id") UUID userId, @Body Map<String, String> updates);
}
