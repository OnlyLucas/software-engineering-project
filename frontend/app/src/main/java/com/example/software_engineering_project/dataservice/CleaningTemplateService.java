package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.CleaningTemplate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface CleaningTemplateService {

    @GET("cleaning-templates/{id}")
    Call<CleaningTemplate> getCleaningTemplate(@Path("id") UUID cleaningTemplateId);

    @POST("cleaning-templates/create-with-cleanings")
    Call<CleaningTemplate> createCleaningTemplateWithCleanings(@Body CleaningTemplate cleaningTemplate);

    @GET("cleaning-templates/group/{group-id}")
    Call<List<CleaningTemplate>> getCleaningTemplates(@Path("group-id") UUID id);

    @DELETE("cleaning-templates/{id}")
    Call<Void> deleteCleaningTemplate(@Path("id") UUID id);
}
