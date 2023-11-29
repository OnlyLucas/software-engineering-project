package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Cleaning;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface CleaningService {

    @GET("cleanings/{id}")
    Observable<Cleaning> getCleaning(@Path("id") UUID cleaningId);

    @POST("cleanings")
    Observable<Cleaning> createCleaning(@Body Cleaning cleaning);
}