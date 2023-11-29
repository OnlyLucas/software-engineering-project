package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.GroupMembership;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface GroupMembershipService {

    @GET("group-memberships/{id}")
    Observable<GroupMembership> getGroupMembership(@Path("id") UUID groupMembershipId);

    @POST("group-memberships")
    Observable<GroupMembership> createGroupMembership(@Body GroupMembership groupMembership);
}