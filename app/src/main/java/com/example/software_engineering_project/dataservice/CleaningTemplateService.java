package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.CleaningTemplate;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface CleaningTemplateService {

    @GET("cleaning-templates/{id}")
    Observable<CleaningTemplate> getCleaningTemplate(@Path("id") UUID cleaningTemplateId);

    @POST("cleaning-templates")
    Observable<CleaningTemplate> createCleaningTemplate(@Body CleaningTemplate cleaningTemplate);
}
