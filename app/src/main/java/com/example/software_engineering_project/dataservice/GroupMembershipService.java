package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.GroupMembership;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface GroupMembershipService {

    @GET("group-memberships/{id}")
    Call<GroupMembership> getGroupMembership(@Path("id") UUID groupMembershipId);

    @POST("group-memberships")
    Call<GroupMembership> createGroupMembership(@Body GroupMembership groupMembership);

    @DELETE("group-memberships/{userId}/{groupId}")
    Call<Void> deleteGroupMembership(@Path("userId") UUID userId, @Path("groupId") UUID groupId);
}