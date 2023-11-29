package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.PaymentsChange;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentsChangeService {

    @GET("payments-changes/{id}")
    Call<PaymentsChange> getPaymentsChange(@Path("id") UUID paymentsChangeId);

    @POST("payments-changes")
    Call<Void> createPaymentsChange(@Body PaymentsChange paymentsChange);
}
