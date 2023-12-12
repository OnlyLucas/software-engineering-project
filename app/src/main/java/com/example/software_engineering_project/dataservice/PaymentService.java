package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.entity.PaymentCreationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    @GET("payments/{id}")
    Call<Payment> getPayment(@Path("id") UUID paymentId);

    @POST("payments/create-with-participations")
    Call<Payment> createPayment(@Body PaymentCreationData payment);

    @GET("payments/group/{group-id}")
    Call<List<Payment>> getPayments(@Path("group-id") UUID groupGroceryId);
}