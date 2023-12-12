package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Cleaning;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface CleaningService {

    @GET("cleanings/{id}")
    Call<Cleaning> getCleaning(@Path("id") UUID cleaningId);

    @POST("cleanings")
    Call<Cleaning> createCleaning(@Body Cleaning cleaning);

    @GET("cleanings/cleaning-template/{templateId}/uncompleted")
    Call<List<Cleaning>> getUncompletedCleaningsForCleaningTemplate(@Path("templateId") UUID templateId);
}