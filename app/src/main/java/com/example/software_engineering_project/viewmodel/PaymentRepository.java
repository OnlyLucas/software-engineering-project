package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import com.example.software_engineering_project.dataservice.PaymentService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.util.ToastUtil;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {

        private PaymentService paymentService;

        public PaymentRepository() {
            // Initialize Retrofit service
            paymentService = RetrofitClient.getInstance().create(PaymentService.class);
            // Fetch group groceries immediately upon repository creation
        }

    public void createPayment(Payment payment, Context context) {
        Call<Payment> call = paymentService.createPayment(payment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    // show toast of success
                    ToastUtil.makeToast("Added " + payment.getName(), context);
                } else {
                    ToastUtil.makeToast("Error while adding  " + payment.getName(), context);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                ToastUtil.makeToast("Error while adding  " + payment.getName(), context);
                // Handle the failure if needed
                // For example, show an error message
            }
        });
    }
}
