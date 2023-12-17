package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Group;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupService {
    @GET("groups/{id}")
    Call<Group> getEntity(@Path("id") UUID groupId);

    @POST("groups")
    Call<Group> createGroup(@Body Group group);
}
