package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginService {
    @GET("login")
    Call<User> login();
}
