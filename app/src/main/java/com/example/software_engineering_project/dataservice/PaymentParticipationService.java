package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.PaymentParticipation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentParticipationService {

    @GET("payment-participations/{id}")
    Call<PaymentParticipation> getPaymentParticipation(@Path("id") UUID paymentParticipationId);

    @POST("payment-participations")
    Call<Void> createPaymentParticipation(@Body PaymentParticipation paymentParticipation);
}