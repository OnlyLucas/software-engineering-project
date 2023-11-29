package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Group;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupService {
    @GET("groups/{id}")
    Observable<Group> getEntity(@Path("id") UUID groupId);

    @POST("groups")
    Observable<Group> createEntity(@Body Group group);
}
