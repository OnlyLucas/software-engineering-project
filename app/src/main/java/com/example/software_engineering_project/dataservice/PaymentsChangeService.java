package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.PaymentsChange;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface PaymentsChangeService {

    @GET("payments-changes/{id}")
    Observable<PaymentsChange> getPaymentsChange(@Path("id") UUID paymentsChangeId);

    @POST("payments-changes")
    Observable<PaymentsChange> createPaymentsChange(@Body PaymentsChange paymentsChange);
}
