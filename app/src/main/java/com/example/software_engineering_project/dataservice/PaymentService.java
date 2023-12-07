package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentService {

    @GET("payments/{id}")
    Call<Payment> getPayment(@Path("id") UUID paymentId);

    @POST("payments")
    Call<Payment> createPayment(@Body Payment payment);
}