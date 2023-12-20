package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.entity.PaymentParticipation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface PaymentParticipationService {

    @GET("payment-participations/{id}")
    Call<PaymentParticipation> getPaymentParticipation(@Path("id") UUID paymentParticipationId);

    @POST("payment-participations")
    Call<PaymentParticipation> createPaymentParticipation(
            @Body PaymentParticipation paymentParticipation
    );

    @GET("payment-participations/group/{groupId}/user/{userId}/get")
    Call<List<Object[]>> getGetPaymentsGroupedByUser(
            @Path("groupId") UUID groupId,
            @Path("userId") UUID userId
    );

    @GET("payment-participations/group/{groupId}/user/{userId}/owe")
    Call<List<Object[]>> getOwePaymentsGroupedByUser(
            @Path("groupId") UUID groupId,
            @Path("userId") UUID userId
    );

    @GET("payment-participations/group/{groupId}/userOwe/{userIdOwe}/userGet/{userIdGet}")
    Call<List<PaymentParticipation>> getGetPaymentParticipationsByUserIds(
            @Path("groupId") UUID groupId,
            @Path("userIdOwe") UUID userIdOwe,
            @Path("userIdGet") UUID userIdGet
    );

    @GET("payment-participations/group/{groupId}/userGet/{userIdGet}/userOwe/{userIdOwe}")
    Call<List<PaymentParticipation>> getOwePaymentParticipationsByUserIds(
            @Path("groupId") UUID groupId,
            @Path("userIdGet") UUID userIdGet,
            @Path("userIdOwe") UUID userIdOwe
    );

    @PUT("payment-participations/{id}")
    Call<PaymentParticipation> updatePaymentParticipation(@Path("id") UUID id, @Body PaymentParticipation p);
}