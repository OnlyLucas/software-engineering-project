package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.User;

import request.UserWithPasswordRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{id}")
    Call<User> getEntity(@Path("id") UUID userId);

    @GET("users/group/{groupId}")
    Call<List<User>> getUsers(@Path("groupId") UUID groupId);

    @POST("users/create-with-password")
    Call<User> createUser(@Body UserWithPasswordRequest user);

    @PATCH("users/change-password")
    Call<User> updatePassword(@Body UserWithPasswordRequest user);

    @PATCH("users/{id}")
    Call<User> partialUpdateEntity(@Path("id") UUID userId, @Body Map<String, String> updates);


    @GET("users/mail/{mail}")
    Call<User> getUserByMail(@Path("mail") String mail);
}
