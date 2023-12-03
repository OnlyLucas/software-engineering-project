package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.GroupGrocery;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface GroupGroceryService {

    @GET("group-groceries/{id}")
    Observable<GroupGrocery> getGroupGrocery(@Path("id") UUID groupGroceryId);

    @GET("group-groceries/group/{group-id}")
    Call<List<GroupGrocery>> getGroupGroceries(@Path("group-id") UUID groupGroceryId);

    @POST("group-groceries")
    Observable<GroupGrocery> createGroupGrocery(@Body GroupGrocery groupGrocery);

    @POST("group-groceries")
    Call<GroupGrocery> createGroupGroceryCall(@Body GroupGrocery groupGrocery);
}
