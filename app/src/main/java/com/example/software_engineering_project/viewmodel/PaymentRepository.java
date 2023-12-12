package com.example.software_engineering_project.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.software_engineering_project.dataservice.PaymentService;
import com.example.software_engineering_project.dataservice.RetrofitClient;
import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.Payment;
import com.example.software_engineering_project.util.ToastUtil;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository {

    private PaymentService paymentService;
    private MutableLiveData<List<Payment>> currentPayments = new MutableLiveData<>();

    public PaymentRepository() {
        // Initialize Retrofit service
        paymentService = RetrofitClient.getInstance().create(PaymentService.class);

        getPayments();
    }

    private void getPayments() {
        // Perform the API call to get users asynchronously
        Group group = UserViewModel.getCurrentGroup().getValue();
        Call<List<Payment>> call = paymentService.getPayments(group.getId());
        call.enqueue(new Callback<List<Payment>>(){
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    List<Payment> payments = response.body();
                    currentPayments.setValue(payments);
                    System.out.println("Payment fetching successful");
                    // Handle the received users, e.g., update UI or store in a local database
                } else {
                    // Handle API error
                    System.out.println("Error while fetching payments");
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                // Handle network failure
                System.out.println("Network error while fetching payments");
            }
        });
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

    public LiveData<List<Payment>> getCurrentPayments() {
        return currentPayments;
    }
}
