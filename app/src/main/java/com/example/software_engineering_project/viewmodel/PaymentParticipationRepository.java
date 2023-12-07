package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import com.example.software_engineering_project.dataservice.PaymentParticipationService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.PaymentParticipation;
import com.example.software_engineering_project.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentParticipationRepository {
    private PaymentParticipationService paymentParticipationService;

    public PaymentParticipationRepository() {
        // Initialize Retrofit service
        paymentParticipationService = RetrofitClient.getInstance().create(PaymentParticipationService.class);

    }

    public void createPaymentParticipation(PaymentParticipation paymentParticipation, Context context) {
        Call<PaymentParticipation> call = paymentParticipationService.createPaymentParticipation(paymentParticipation);
        call.enqueue(new Callback<PaymentParticipation>() {
            @Override
            public void onResponse(Call<PaymentParticipation> call, Response<PaymentParticipation> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Success", context);
                } else {
                    ToastUtil.makeToast("Error while adding payment participation ", context);
                }
            }

            @Override
            public void onFailure(Call<PaymentParticipation> call, Throwable t) {
                ToastUtil.makeToast("Error while adding payment participation", context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }

}
