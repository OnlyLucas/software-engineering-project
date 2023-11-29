package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.GroupGrocery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface GroupGroceryService {

    @GET("group-groceries/{id}")
    Call<GroupGrocery> getGroupGrocery(@Path("id") UUID groupGroceryId);

    @POST("group-groceries")
    Call<Void> createGroupGrocery(@Body GroupGrocery groupGrocery);
}
