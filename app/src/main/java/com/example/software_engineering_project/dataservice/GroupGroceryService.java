package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.GroupGrocery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface GroupGroceryService {

    @GET("group-groceries/{id}")
    Call<GroupGrocery> getGroupGrocery(@Path("id") UUID groupGroceryId);

    @GET("group-groceries/group/{group-id}")
    Call<List<GroupGrocery>> getGroupGroceries(@Path("group-id") UUID groupGroceryId);

    @GET("group-groceries/group/{group-id}/uncompleted")
    Call<List<GroupGrocery>> getUncompletedGroupGroceries(@Path("group-id") UUID groupGroceryId);

    @GET("group-groceries/group/{group-id}/completed")
    Call<List<GroupGrocery>> getCompletedGroupGroceries(@Path("group-id") UUID groupGroceryId);

    @POST("group-groceries")
    Call<GroupGrocery> createGroupGrocery(@Body GroupGrocery groupGrocery);

    @DELETE("group-groceries/{id}")
    Call<Void> deleteGroupGrocery(@Path("id") UUID groupGroceryId);

    @PUT("group-groceries/{id}")
    Call<GroupGrocery> updateGroupGrocery(@Path("id") UUID groupGroceryId, @Body GroupGrocery groupGrocery);

}
