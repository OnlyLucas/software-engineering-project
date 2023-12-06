package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.Payment;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentService {

    @GET("payments/{id}")
    Observable<Payment> getPayment(@Path("id") UUID paymentId);

    @POST("payments")
    Observable<Payment> createPayment(@Body Payment payment);
}