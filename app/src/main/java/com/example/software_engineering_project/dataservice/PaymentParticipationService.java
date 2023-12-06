package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.PaymentParticipation;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentParticipationService {

    @GET("payment-participations/{id}")
    Observable<PaymentParticipation> getPaymentParticipation(@Path("id") UUID paymentParticipationId);

    @POST("payment-participations")
    Observable<PaymentParticipation> createPaymentParticipation(@Body PaymentParticipation paymentParticipation);
}