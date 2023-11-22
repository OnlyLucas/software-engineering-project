package com.example.software_engineering_project.dataservice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EntityService<T> {
    @GET("{entity}/{id}")
    Call<T> getEntity(@Path("entity") Class<T> entityClass, @Path("id") String entityId);

    @POST("{entity}")
    Call<T> createEntity(@Path("entity") Class<T> entityClass, @Body T entityBody);

    // Add other API endpoints as needed
}
