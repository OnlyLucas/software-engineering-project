package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.CleaningTemplate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface CleaningTemplateService {

    @GET("cleaning-templates/{id}")
    Call<CleaningTemplate> getCleaningTemplate(@Path("id") UUID cleaningTemplateId);

    @POST("cleaning-templates")
    Call<Void> createCleaningTemplate(@Body CleaningTemplate cleaningTemplate);
}
